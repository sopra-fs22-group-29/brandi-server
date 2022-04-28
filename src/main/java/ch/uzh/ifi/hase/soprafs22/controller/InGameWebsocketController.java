package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.websocket.Move;
import ch.uzh.ifi.hase.soprafs22.rest.dto.websocket.MoveGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.websocket.MovePostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.InGameWebsocketService;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

/* import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery; */

@Controller
public class InGameWebsocketController {

    /* static EntityManagerFactory emf = Persistence.createEntityManagerFactory(""); */

    private final InGameWebsocketService inGameWebsocketService;
    private final GameService gameService;

    InGameWebsocketController(InGameWebsocketService service, GameService gameService) {
        this.inGameWebsocketService = service;
        this.gameService = gameService;
    }


    // TODO: need to add {uuid} and notify all other players (refer to test function below)
    @MessageMapping("/websocket/{uuid}/move")
    public void move(@DestinationVariable String uuid, MovePostDTO MovePostDTO, Principal principal) throws Exception {
        // get move from the client
        Move move = DTOMapper.INSTANCE.convertMovePostDTOtoEntity(MovePostDTO);

        // verify move validity and add Player details
        String username = principal.getName();

        /* EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Game> g = em.createQuery("SELECT g FROM Game g LEFT JOIN FETCH g.playerStates", Game.class);
        List<Game> games = g.getResultList();
        Game userGame = null;
        for(Game game : games){
            if(game.getUuid().equals(uuid)){
                userGame = game;
                break;
            }
        } */


        Game userGame = gameService.getGameByUuid(uuid);

        move = inGameWebsocketService.verifyMove(userGame, move, username);
        MoveGetDTO moveDTO = DTOMapper.INSTANCE.convertEntityToMoveGetDTO(move);
        
        inGameWebsocketService.notifyAllGameMembers("/client/move", userGame, moveDTO.toString()); 
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

        Game game = gameService.getGameByUuid(uuid);

        // the payload can be a anything you want to send to the clients
        inGameWebsocketService.notifyAllGameMembers("/client/test", game, principal.getName() + " sent a test message!");
    }

}
