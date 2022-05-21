package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.Color;
import ch.uzh.ifi.hase.soprafs22.entity.Ball;
import ch.uzh.ifi.hase.soprafs22.entity.Card;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.GameGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.IdDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.GameLogicService;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;


/**
 * Game Controller
 * This class is responsible for handling all REST request that are related to
 * the game.
 * The controller will receive the request and delegate the execution to the
 * GameService and finally return the result.
 */

@RestController
public class GameController {

    private final GameService gameService;
    private GameLogicService gameLogicService;
    private UserService userService;

    GameController(GameService gameService, UserService userService, GameLogicService gameLogicService) {
        this.gameService = gameService;
        this.gameLogicService = gameLogicService;
        this.userService = userService;
    }

    @PostMapping("/game")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    // Create game and add first user to it
    public String createGame(@RequestBody IdDTO lobbyLeaderId) {

        System.out.println("/game called");

        return gameService.createGame(lobbyLeaderId.getId());
    }

    @PutMapping("/game/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Boolean joinGame(@PathVariable(name = "uuid") String uuid, Principal principal) {
        String username = principal.getName();
        Boolean success = gameService.joinGame(uuid, username);
        return success;
    }

    //For testing purposes, state should probably be taken from Websocket by clients
    @GetMapping("/game")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<GameGetDTO> getGames() {
        List<Game> allGames = gameService.getGames();
        List<GameGetDTO> allGamesDTO = new ArrayList<>();
        for(Game game : allGames){
            allGamesDTO.add(DTOMapper.INSTANCE.convertEntityToGameGetDTO(game));
        }
        
        return allGamesDTO;
    }

    @GetMapping("/game/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO getGameByUuid(@PathVariable(name = "uuid") String uuid, Principal principal) {
        Game game = gameService.getGameByUuidOfUser(uuid, principal.getName());
        if(game == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "game not found by uuid");
        }
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);
    }

    @GetMapping("/game/{uuid}/color")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Color getColorOfUserInGame(@PathVariable(name = "uuid") String uuid, @RequestBody IdDTO id){
        return gameService.getColorOfUserInGame(uuid, id.getId());
    }

    @GetMapping("/game/{uuid}/movePossible")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Boolean getMovePossible(@PathVariable(name = "uuid") String uuid, Principal principal){
        String username = principal.getName();
        Game game = gameService.getGameByUuidOfUser(uuid, principal.getName());
        User user = userService.getUser(username);

        // If move possible with any of the cards in players hand -> return true
        for(Card card: game.getPlayerState(username).getPlayerHand().getActiveCards()){
            Set<Ball> balls = game.getBoardstate().getBalls();
            Color userColor = gameService.getColorOfUserInGame(uuid, user.getId());
            Color teamMateColor = game.getColorOfTeammate(userColor);

            if(!gameLogicService.highlightBalls(game, card.getRank(), balls, userColor, teamMateColor).isEmpty()){
                // Move possible with that card
                return true;
            }
        }

        return false;
    }

}
