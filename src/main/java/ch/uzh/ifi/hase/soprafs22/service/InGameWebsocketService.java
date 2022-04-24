package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.entity.websocket.Move;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs22.repository.LobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@Transactional
public class InGameWebsocketService {
    
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final LobbyRepository lobbyRepository;

    @Autowired
    public InGameWebsocketService(@Qualifier("gameRepository") GameRepository gameRepository,
    @Qualifier("userRepository") UserRepository userRepository,
    @Qualifier("lobbyRepository") LobbyRepository lobbyRepository) {

        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.lobbyRepository = lobbyRepository;
    }

    public Move verifyMove(Move move, String username, Long gameId){
        // Assign user to move, make move in Game, return move
        User user = userRepository.findByUsername(username);
        move.setUser(user);
        Optional<Game> optGame = gameRepository.findById(gameId);
        Game game;
        if(optGame.isPresent()){
            game = optGame.get();
            game.makeMove(move);
            gameRepository.saveAndFlush(game);
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Something went wrong when executing your move");
        }
        
        return move;
    }
}
