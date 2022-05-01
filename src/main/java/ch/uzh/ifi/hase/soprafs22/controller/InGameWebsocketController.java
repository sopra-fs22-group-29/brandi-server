package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.Color;
import ch.uzh.ifi.hase.soprafs22.entity.Ball;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.PlayerState;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.entity.websocket.Move;
import ch.uzh.ifi.hase.soprafs22.rest.dto.*;
import ch.uzh.ifi.hase.soprafs22.rest.dto.websocket.MoveGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.websocket.MovePostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.websocket.SelectMarbleResponseDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.GameLogicService;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.InGameWebsocketService;

import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Random;
import java.util.Set;


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
        Game userGame = gameService.getGameByUuid(uuid, username);

        // verify move validity and add Player details to move for returning
        move = inGameWebsocketService.verifyMove(userGame, move, username);
        MoveGetDTO moveDTO = DTOMapper.INSTANCE.convertEntityToMoveGetDTO(move);
        
        inGameWebsocketService.notifyAllGameMembers("/client/move", userGame, moveDTO); 

        userGame = gameService.getGameByUuid(uuid, username);

        PlayerState nextUser = userGame.getNextTurn();
        if(nextUser == null){
            // Send new Cards to all users
            gameService.startNewRound(uuid);
            for(PlayerState playerState: userGame.getPlayerStates()){
                // TODO: Return a DTO
                inGameWebsocketService.notifySpecificUser("/client/cards", playerState.getPlayer().getUsername(), playerState.getPlayerHand());
            }
        } else{
            // Send next user to all users
            inGameWebsocketService.notifyAllGameMembers("/client/nextPlayer", userGame, nextUser.getPlayer());
        }
    }

    @MessageMapping("/websocket/{uuid}/surrenderCards")
    public void surrenderCards(@DestinationVariable String uuid, Principal principal) throws Exception {
        String username = principal.getName();
        gameService.surrenderCards(uuid, username);
    }

    @MessageMapping("/websocket/{uuid}/join")
    public void joinGameByUuid(@DestinationVariable String uuid, Principal principal) throws Exception {
        System.out.println(principal.getName() + " just joined a game");
        Game game = gameService.getGameByUuid(uuid, principal.getName());
        if(game == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "game not found by uuid");
        }

        PlayerState playerState = game.getPlayerState(principal.getName());

        // provide the new user with the current Game State
        inGameWebsocketService.notifySpecificUser("/client/state", principal.getName(), DTOMapper.INSTANCE.convertEntityToGameGetDTO(game));

        // provide the new user with the his hand
        inGameWebsocketService.notifySpecificUser("/client/cards", principal.getName(), playerState.getPlayerHand());

        // provide the user's information to all other members in the lobby
        UserGetDTO user = DTOMapper.INSTANCE.convertEntityToUserGetDTO(userService.getUser(principal.getName()));
        inGameWebsocketService.notifyAllOtherGameMembers("/client/player/joined", game, principal.getName(), playerState);
    }

    @MessageMapping("/websocket/{uuid}/select/card")
    public void selectCard(@DestinationVariable String uuid, CardDTO card, Principal principal) throws Exception {
        System.out.println(principal.getName() + " selected a card");

        Game game = gameService.getGameByUuid(uuid, principal.getName());
        if(game == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "game not found by uuid");
        }

        PlayerState playerState = game.getPlayerState(principal.getName());

        // choose marbles adequately to chosen card

        Set<Ball> balls = game.getBoardstate().getBalls();

        Set<Integer> marblesSet = gameLogicService.highlightBalls(card.getRank(), balls, playerState.getColor());

        int[] marbles = marblesSet.stream().mapToInt(Integer::intValue).toArray();

        HighlightMarblesDTO highlightMarblesDTO = new HighlightMarblesDTO();
        highlightMarblesDTO.setIndex(card.getIndex());
        highlightMarblesDTO.setMarbles(marbles);

        // provide the user with a list of marbles he could move
        inGameWebsocketService.notifySpecificUser("/client/highlight/marbles", principal.getName(), highlightMarblesDTO);
    }


    @MessageMapping("/websocket/{uuid}/select/marble")
    public void selectMarble(@DestinationVariable String uuid, SelectMarbleDTO selectMarbleDTO, Principal principal) throws Exception {
        System.out.println(principal.getName() + " selected a marble");

        Game game = gameService.getGameByUuid(uuid, principal.getName());
        if(game == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "game not found by uuid");
        }

        PlayerState playerState = game.getPlayerState(principal.getName());

        // chose random tiles on the board
        Random rand = new Random();
        int howMany = rand.nextInt(3) + 1;
        int[] highlightedHoles = new int[howMany];
        int index = 0;
        for (int i = 0; i < howMany; i++) {
            highlightedHoles[i] = rand.nextInt(64);
        }

        SelectMarbleResponseDTO selectMarbleResponseDTO = new SelectMarbleResponseDTO();
        selectMarbleResponseDTO.setMarbleId(selectMarbleDTO.getMarbleId());
        selectMarbleResponseDTO.setHighlightHoles(highlightedHoles);

        // provide the user with a list of marbles he could move
        inGameWebsocketService.notifySpecificUser("/client/highlight/holes", principal.getName(), selectMarbleResponseDTO);
    }


    /**
     * We can use a method of this form to send a board state update to all the players that are currently connected
     * to the same game.
     *
     * @param principal Authenticated user information
     */
    @MessageMapping("/websocket/{uuid}/test")
    public void test(@DestinationVariable String uuid, Principal principal) throws Exception {

        // we still have to verify if the player is actually playing in the game with that uuid

        Game game = gameService.getGameByUuid(uuid, principal.getName());

        // the payload can be a anything you want to send to the clients
        inGameWebsocketService.notifyAllGameMembers("/client/test", game, principal.getName() + " sent a test message!");
    }

}
