package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.Color;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.PlayerState;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Game Service
 * This class is the "worker" and responsible for all functionality related to
 * the game
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class GameService {

    private final Logger log = LoggerFactory.getLogger(GameService.class);
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Autowired
    public GameService(@Qualifier("gameRepository") GameRepository gameRepository,
    @Qualifier("userRepository") UserRepository userRepository) {

        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public String createGame(Long userId, String name) {
        Optional<User> optUser = this.userRepository.findById(userId);
        if(optUser.isPresent()){
            // Create Game and set passed user as player in that game, return game
            User user = optUser.get();
            Game newGame = new Game(user);
            newGame.setName(name);
            newGame.getPlayerStates().get(0).setIsPlaying(true);
            newGame = gameRepository.saveAndFlush(newGame);
            
            // Add game to list of games in user, persist in DB
            user.addGame(newGame);
            userRepository.saveAndFlush(user);
            System.out.println("Created Information for Game: " + newGame.getUuid());
            return newGame.getUuid();
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find Lobbyleader");
        }
        
    }

    public Boolean joinGame(String uuid, String username){
        Optional<Game> optGame = gameRepository.findByUuid(uuid);
        if(optGame.isPresent()){
            // If game exists, add User to game and persist in DB
            Game game = optGame.get();
            User user = userRepository.findByUsername(username);

            /* Boolean added = 
             *  true if user wasn't in game before and was added
             *  false if user was already in game and no action was taken 
             */
            Boolean added = game.addPlayer(user);

            if(added){
                game = gameRepository.saveAndFlush(game);
                System.out.println(String.format("Added user %s to game %d, uuid = &s", username, game.getId(), game.getUuid()));

                // Add game to list of games in user, persist in DB
                user.addGame(game);
                userRepository.saveAndFlush(user);
                return true;
            }
            return false;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find Game");
        }
    }
    

    
    /**
     * 
     * Use when user needs to be in game of uuid
     * @param uuid of game to get
     * @param username of user that initiated request
     * @return game iff. username is in that game
     */
    public Game getGameByUuidOfUser(String uuid, String username) {
        Optional<Game> optGame = gameRepository.findByUuid(uuid);
        if(optGame.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find game");
        }

        Game game = optGame.get();
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + username + " not found");
        }
        
        if(!user.isInGame(uuid)){ // check whether user is in the game
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, username + " is not in game " + uuid);
        }

        return game;
    }

    /**
     * 
     * @param uuid
     * @return Game
     * @throws Exception if  game with uuid does not exist
     */
    public Game getGameByUuid(String uuid) {
        Optional<Game> optGame = gameRepository.findByUuid(uuid);
        if(optGame.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find game");
        }
        return optGame.get();
    }

    public List<Game> getGames() {
        return gameRepository.findAll();
    }

    public Color getColorOfUserInGame(String uuid, Long id) {
        Optional<Game> optGame = gameRepository.findByUuid(uuid);
        if(optGame.isPresent()){
            Game game = optGame.get();
            Optional<Color> optColor = game.getUserColorById(id);
            if(optColor.isPresent()){
                return optColor.get();
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not in that game");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
    }
    
        /**
     * sets the player status online
     * @param game
     * @param playerName
     * @return the PlayerState
     */
    public PlayerState playerJoined(Game game, String playerName) {
        // set player online status
        PlayerState playerState = game.getPlayerState(playerName);
        playerState.setPlayerStatus(true);
        gameRepository.saveAndFlush(game);
        return playerState;
    }

    /**
     * sets the player status offline
     * @param game
     * @param playerName
     * @return the PlayerState
     */
    public PlayerState playerLeft(Game game, String playerName) {
        // set player online status
        PlayerState playerState = game.getPlayerState(playerName);
        playerState.setPlayerStatus(false);
        gameRepository.saveAndFlush(game);
        return playerState;
    }

    public void endGame(Game game) {
        game.endGame();
        gameRepository.saveAndFlush(game);
    }
}