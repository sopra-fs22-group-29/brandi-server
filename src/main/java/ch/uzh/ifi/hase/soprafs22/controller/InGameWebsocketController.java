package ch.uzh.ifi.hase.soprafs22.controller;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import ch.uzh.ifi.hase.soprafs22.constant.Color;
import ch.uzh.ifi.hase.soprafs22.constant.Rank;
import ch.uzh.ifi.hase.soprafs22.entity.Ball;
import ch.uzh.ifi.hase.soprafs22.entity.BoardState;
import ch.uzh.ifi.hase.soprafs22.entity.Card;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.PlayerState;
import ch.uzh.ifi.hase.soprafs22.entity.websocket.Move;
import ch.uzh.ifi.hase.soprafs22.rest.dto.CardDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SelectMarbleDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.websocket.MovePostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.websocket.SelectMarbleResponseDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.GameLogicService;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.InGameWebsocketService;
import ch.uzh.ifi.hase.soprafs22.service.UserService;


@Controller
public class InGameWebsocketController {

    private final InGameWebsocketService inGameWebsocketService;
    private final GameLogicService gameLogicService;
    private final GameService gameService;
    private UserService userService;

    InGameWebsocketController(InGameWebsocketService service, GameService gameService, GameLogicService gameLogicService, UserService userService) {
        this.inGameWebsocketService = service;
        this.gameLogicService = gameLogicService;
        this.gameService = gameService;
        this.userService = userService;
    }


    @MessageMapping("/websocket/{uuid}/move")
    public void move(@DestinationVariable String uuid, MovePostDTO MovePostDTO, Principal principal) throws Exception {
        // Get move, username, game
        Move move = DTOMapper.INSTANCE.convertMovePostDTOtoEntity(MovePostDTO);
        String username = principal.getName();
        Game game = gameService.getGameByUuidOfUser(uuid, username);

        if(!inGameWebsocketService.checkCanUseCard(game, move.getPlayedCard())) return;

        if(move.getPlayedCard().getRank().equals(Rank.SEVEN)){
            Ball ball = game.getBoardstate().getBallById(move.getBallId());
            // TODO: SHould withDest be true or false??
            List<Integer> holesTraveled = GameLogicService.getHolesTravelled(move.getDestinationTile(), ball.getPosition(), false);
            game = inGameWebsocketService.addHolesTravelled(game, holesTraveled.size());
        }

        // verify move validity, make move in game, add Player details to move for returning
        move = inGameWebsocketService.verifyMove(game, move, username);

        // move == null means it wasnt users turn or no cards left, simply ignore 
        if(move == null) return;

        // Need to pass marblesset if card played was a seven
        Set<Ball> balls = game.getBoardstate().getBalls();
        Color userColor = game.getPlayerState(username).getColor();
        Set<Integer> marblesSet = gameLogicService.highlightBalls(game, move.getPlayedCard().getRank(), balls, userColor, game.getColorOfTeammate(userColor));

        //Not great to fetch again
        game = gameService.getGameByUuidOfUser(uuid, username);
        inGameWebsocketService.notifyPlayersAfterMove(game, move, marblesSet);
        //check if move possible
        Boolean movePossible = checkMovePossibleWithAnyCard(game, game.getNextTurn().getPlayer().getUsername());
        inGameWebsocketService.notifySpecificUser("/client/movePossible", game.getNextTurn().getPlayer().getUsername(), movePossible);

    }

    @MessageMapping("/websocket/{uuid}/join")
    public void joinGameByUuid(@DestinationVariable String uuid, Principal principal) throws Exception {
        System.out.println(principal.getName() + " just joined a game");
        Game game = gameService.getGameByUuid(uuid);
        if(game == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game to join not found by uuid");
        }

        PlayerState playerState = gameService.playerJoined(game, principal.getName());

        // if(playerState == null) return;

        // provide the new user with the current Game State
        inGameWebsocketService.notifySpecificUser("/client/state", principal.getName(), DTOMapper.INSTANCE.convertEntityToGameGetDTO(game));

        // provide the new user with the his hand
        inGameWebsocketService.notifySpecificUser("/client/cards", principal.getName(), playerState.getPlayerHand());

        // provide the user's updated information to all other members in the lobby
        UserGetDTO user = DTOMapper.INSTANCE.convertEntityToUserGetDTO(userService.getUser(principal.getName()));
        inGameWebsocketService.notifyAllOtherGameMembers("/client/player/joined", game, principal.getName(), playerState);
        Boolean movePossible = checkMovePossibleWithAnyCard(game, principal.getName());
        inGameWebsocketService.notifySpecificUser("/client/movePossible", principal.getName(), movePossible);
    }


    @MessageMapping("/websocket/{uuid}/leave")
    public void leaveGameByUuid(@DestinationVariable String uuid, Principal principal) throws Exception {
        System.out.println(principal.getName() + " just left a game");
        Game game = gameService.getGameByUuidOfUser(uuid, principal.getName());
        if(game == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "game not found by uuid");
        }

        PlayerState playerState = gameService.playerLeft(game, principal.getName());

        // provide the user's updated information to all other members in the lobby
        UserGetDTO user = DTOMapper.INSTANCE.convertEntityToUserGetDTO(userService.getUser(principal.getName()));
        inGameWebsocketService.notifyAllOtherGameMembers("/client/player/left", game, principal.getName(), playerState);
    }

    @MessageMapping("/websocket/{uuid}/pauseGame")
    public void pauseGame(@DestinationVariable String uuid, Principal principal) throws Exception{
        Game game = gameService.getGameByUuidOfUser(uuid, principal.getName());
        if(game == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "game not found by uuid");
        }
        game.pauseGame();
        inGameWebsocketService.notifyAllOtherGameMembers("/client/pauseGame", game, principal.getName(), game);

    }
    @MessageMapping("/websocket/{uuid}/surrender")
    public void surrender(@DestinationVariable String uuid, Principal principal) throws Exception{
        Game game = gameService.getGameByUuidOfUser(uuid, principal.getName());
        if(game == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "game not found by uuid");
        }
        gameService.endGame(game);
        inGameWebsocketService.notifyAllOtherGameMembers("/client/surrender", game, principal.getName(), game);

    }

    @MessageMapping("/websocket/{uuid}/select/card")
    public void selectCard(@DestinationVariable String uuid, CardDTO card, Principal principal) throws Exception {
        System.out.println(principal.getName() + " selected a card");

        Game game = gameService.getGameByUuidOfUser(uuid, principal.getName());
        if(game == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "game not found by uuid");
        }
        
        String username = principal.getName();
        Set<Ball> balls = game.getBoardstate().getBalls();
        Color userColor = game.getPlayerState(username).getColor();

        Set<Integer> marblesSet = gameLogicService.highlightBalls(game, card.getRank(), balls, userColor, game.getColorOfTeammate(userColor));

        Boolean movePossible = inGameWebsocketService.selectCard(game, card, username, marblesSet);
        if(movePossible){
            return;
        }
        // If user can choose other card and play with that, return nothing. 
        if(!checkMovePossibleWithAnyCard(game, username)){
            return;
        }

        // If no playable card, delete cards and move to next player
        inGameWebsocketService.noMovePossible(game, username);
    }


    @MessageMapping("/websocket/{uuid}/select/marble")
    public void selectMarble(@DestinationVariable String uuid, SelectMarbleDTO selectMarbleDTO, Principal principal) throws Exception {
        System.out.println(principal.getName() + " selected a marble");

        Game game = gameService.getGameByUuidOfUser(uuid, principal.getName());
        if(game == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "game not found by uuid");
        }

        PlayerState playerState = game.getPlayerState(principal.getName());

        // highlight possible moves

        BoardState boardState = game.getBoardstate();

        Set<Ball> balls = boardState.getBalls();

        Long ballId = Long.valueOf(selectMarbleDTO.getMarbleId());

        Ball ball = boardState.getBallById(ballId);

        Set<Integer> possibleMoves = GameLogicService.getPossibleMoves(game, selectMarbleDTO.getRank(), balls, ball);

        Set<Integer> highlightedHolesSet = gameLogicService.getPossibleDestinations(possibleMoves, ball, balls);

        int[] highlightedHoles = highlightedHolesSet.stream().mapToInt(Integer::intValue).toArray();

        SelectMarbleResponseDTO selectMarbleResponseDTO = new SelectMarbleResponseDTO();
        selectMarbleResponseDTO.setMarbleId(selectMarbleDTO.getMarbleId());
        selectMarbleResponseDTO.setHighlightHoles(highlightedHoles);

        // provide the user with a list of marbles he could move
        inGameWebsocketService.notifySpecificUser("/client/highlight/holes", principal.getName(), selectMarbleResponseDTO);
    }

    @MessageMapping("/websocket/{uuid}/surrenderCards")
    public void surrenderCards(@DestinationVariable String uuid, Principal principal){
        String username = principal.getName();
        Game game = gameService.getGameByUuidOfUser(uuid, username);

        inGameWebsocketService.noMovePossible(game, username);
    }

    private Boolean checkMovePossibleWithAnyCard(Game game, String username){
        Set<Ball> balls = game.getBoardstate().getBalls();
        Color userColor = game.getPlayerState(username).getColor();
        Color teammateColor = game.getColorOfTeammate(userColor);
        PlayerState playerState = game.getPlayerState(username);
        if(playerState.getPlayerHand().getActiveCards().isEmpty()){
            return true;
        }
        else{
        for(Card cardInHand: playerState.getPlayerHand().getActiveCards()){
            Set<Integer> possibleMarbles = gameLogicService.highlightBalls(game, cardInHand.getRank(), balls, userColor, teammateColor);
            //TODO: Could send list of playable cards to user here
            if(!possibleMarbles.isEmpty()){
                // User has other card to make a move, ignore 
                return true;
            }
        }
        return false;
        }
    }
}
