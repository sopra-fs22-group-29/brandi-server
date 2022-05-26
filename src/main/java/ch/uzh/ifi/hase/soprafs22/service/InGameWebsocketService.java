package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.Rank;
import ch.uzh.ifi.hase.soprafs22.entity.Ball;
import ch.uzh.ifi.hase.soprafs22.entity.Card;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.PlayerState;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.entity.websocket.Move;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.CardDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.HighlightMarblesDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.websocket.MoveGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class InGameWebsocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Autowired
    public InGameWebsocketService(@Qualifier("gameRepository") GameRepository gameRepository,
    @Qualifier("userRepository") UserRepository userRepository) {

        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public void notifyAllGameMembers(String route, Game game, /*Principal principal,*/ Object payload) {
        List<String> sentTo = new ArrayList<>();
        game.getPlayerStates().forEach((playerState) -> {
            sentTo.add(playerState.getPlayer().getUsername());
        });

        sentTo.forEach((send) -> {
            simpMessagingTemplate.convertAndSendToUser(send, route, payload);
        });
    }

    /**
     * Send a notification to all game members except 'userName'
     * @param route
     * @param game
     * @param userName
     * @param payload
     */
    public void notifyAllOtherGameMembers(String route, Game game, String userName, Object payload) {
        List<String> sentTo = new ArrayList<>();
        game.getPlayerStates().forEach((playerState) -> {
            sentTo.add(playerState.getPlayer().getUsername());
        });

        sentTo.forEach((send) -> {
            if(!send.equals(userName)) {
                simpMessagingTemplate.convertAndSendToUser(send, route, payload);
            }
        });
    }

    public void notifySpecificUser(String route, String userName, Object payload) {
        simpMessagingTemplate.convertAndSendToUser(userName, route, payload);
    }


   /* Assign user to move, make move in Game, return move */
    public Move verifyMove(Game game, Move move, String username){
        // Add user details to move so that everybody knows who made the move
        User user = userRepository.findByUsername(username);
        if(this.checkHasNoCardsLeft(game, username)){
            System.out.println(username + " has no cards left to make a move");
            return null;
        }

        if(!this.checkGameFull(game)) return null;

        // User is not nextUser to play
        String nextPlayer = game.getNextTurn().getPlayer().getUsername();
        if(!nextPlayer.equals(username)){
            return null;
        }

        if(!this.checkCanUseCard(game, move.getPlayedCard())) return null;

        move.setUser(user);
        // Actually make the move and persist it
        game.makeMove(move);

        gameRepository.saveAndFlush(game);
    
        // If everything went well, return the move that was made
        return move;
    }
    
    public void notifyPlayersAfterMove(Game game, Move move, Set<Integer> marblesSet) {
        MoveGetDTO moveDTO = DTOMapper.INSTANCE.convertEntityToMoveGetDTO(move);
        
        this.notifyAllGameMembers("/client/move", game, moveDTO); 
        
        PlayerState nextUser = game.getNextTurn();
        
        // Game is over: Send winning team to all users in game
        if(game.getGameOver()){
            Integer winningTeam = game.getWinnerTeam();
            for(PlayerState state: game.getPlayerStates()){
                String username = state.getPlayer().getUsername();
                // TODO: Should we send string "You won/lost" or boolean here?
                if(state.getTeam().equals(winningTeam)){
                    this.notifySpecificUser("/client/gameOver", username, true);
                } else{
                    this.notifySpecificUser("/client/gameOver", username, false);
                }
            }
            return;
        }
        
        String username = move.getUser().getUsername();

        // No user can play any cards anymore -> Start new round
        if(nextUser == null){ 
            game.startNewRound();
            nextUser = game.getNextTurn();
            game = gameRepository.saveAndFlush(game);
            for(PlayerState playerState: game.getPlayerStates()){
                // Send new Cards to all users
                this.notifySpecificUser("/client/cards", playerState.getPlayer().getUsername(), playerState.getPlayerHand());
                // Send next user to all users
                //TODO: should maybe be moved to outside of loop
                this.notifyAllGameMembers("/client/nextPlayer", game, nextUser.getPlayer());
            }
        // User can go again (SEVEN), send marbles to make a move with
        } else if(move.getUser().getId().equals(nextUser.getPlayer().getId())
                && game.getLastCardPlayed() != null 
                && (game.getLastCardPlayed().equals(move.getPlayedCard())
                    || game.getLastCardPlayed().getId().equals(move.getCardId()))){

            
            int[] marbles = marblesSet.stream().mapToInt(Integer::intValue).toArray();

            HighlightMarblesDTO highlightMarblesDTO = new HighlightMarblesDTO();
            highlightMarblesDTO.setIndex(move.getIndex());
            highlightMarblesDTO.setMarbles(marbles);

            // provide the user with a list of marbles he could move
            this.notifySpecificUser("/client/highlight/marbles", username, highlightMarblesDTO);
            
        // Normal case: User made a move, nextUser can now make a move
        }  else {
            // Send next user to all users, send updated cards to user that moved
            this.notifyAllGameMembers("/client/nextPlayer", game, nextUser.getPlayer());
            PlayerState state = game.getPlayerState(username);
            this.notifySpecificUser("/client/cards", username, state.getPlayerHand());
        }

    }

    public Boolean checkIsNext(Game game, String username){
        // User is not nextUser to play
        String nextPlayer = game.getNextTurn().getPlayer().getUsername();
        if(nextPlayer.equals(username)){
            return true;
        }
        return false;
    }

    /* public void checkForSurrender(String username, Game game){
        PlayerState state = game.getPlayerState(username);
        Set<Ball> balls = game.getBoardstate().getBalls();
        // If user can choose other card and play with that, return nothing. If no playable card, delete cards and move to next player
        for(Card cardInHand: state.getPlayerHand().getActiveCards()){
            Set<Integer> possibleMarbles = gameLogicService.highlightBalls(cardInHand.getRank(), balls, state.getColor());
            if(!possibleMarbles.isEmpty()){
                return;
            }
        }
        // delete cards and move to next player
        game.surrenderCards(username);
        gameRepository.saveAndFlush(game);
    } */

    public Boolean checkHasNoCardsLeft(Game game, String username){
        Set<Card> cards = game.getPlayerState(username).getPlayerHand().getActiveCards();
        return cards.isEmpty();
    }

    public Boolean selectCard(Game game, CardDTO cardDTO, String username, Set<Integer> marblesSet){
        Card card = DTOMapper.INSTANCE.convertCardDTOToEntity(cardDTO);

        if(!this.checkCanUseCard(game, card)) return true;
        if(!this.checkGameFull(game)) return true;

        PlayerState playerState = game.getPlayerState(username);

        // choose marbles adequately to chosen card
        Set<Ball> balls = game.getBoardstate().getBalls();

        // check if his turn
        Boolean isNext = this.checkIsNext(game, username);
        if(!isNext) return true;
        
        if(marblesSet.isEmpty()){
            return false;
        }
        
        if(card.getRank().equals(Rank.SEVEN)){
            // Sum max move with each marble that user can move with
            // If less than 7 -> User will not be able to move 7 holes -> Isn't allowed to use that card
            Integer allPossibleMovesAdded = 0;
            for(Integer ballPos: marblesSet){
                Ball ball = game.getBoardstate().getBallByPosition(ballPos);
                Set<Integer> possibleMoves =  GameLogicService.getPossibleMoves(game, card.getRank(), balls, ball);

                Integer longestMoveWithBall = Collections.max(possibleMoves);
                allPossibleMovesAdded += longestMoveWithBall;
            }
            if(allPossibleMovesAdded < 7) return false;
        }
        

        int[] marbles = marblesSet.stream().mapToInt(Integer::intValue).toArray();

        HighlightMarblesDTO highlightMarblesDTO = new HighlightMarblesDTO();
        highlightMarblesDTO.setIndex(cardDTO.getIndex());
        highlightMarblesDTO.setMarbles(marbles);

        // provide the user with a list of marbles he could move
        this.notifySpecificUser("/client/highlight/marbles", username, highlightMarblesDTO);
        return true;
    }

    /**
     * surrender cards, move to next user
     * @param game
     * @param username
     */
    public void noMovePossible(Game game, String username){
        // Need to refetch game here because no proxy error otherwise
        Optional<Game> optGame = gameRepository.findByUuid(game.getUuid());
        if(optGame.isEmpty()) return;
        game = optGame.get();

        // User can choose no other card and play with that: Delete cards
        game = this.surrenderCards(game, username);
        // Move to next user
        PlayerState nextUser = game.getNextTurn();
        if(nextUser == null){
            // Send new Cards to all users
            game.startNewRound();
            game = gameRepository.saveAndFlush(game);
            nextUser = game.getNextTurn();
            for(PlayerState state: game.getPlayerStates()){
                this.notifySpecificUser("/client/cards", state.getPlayer().getUsername(), state.getPlayerHand());
                this.notifyAllGameMembers("/client/nextPlayer", game, nextUser.getPlayer());
            }
        } else{
            // Send next user to all users
            this.notifyAllGameMembers("/client/nextPlayer", game, nextUser.getPlayer());
            PlayerState state = game.getPlayerState(username);
            this.notifySpecificUser("/client/cards", username, state.getPlayerHand());
        }
    }

    private Game surrenderCards(Game game, String username) {  
        game.surrenderCards(username);
        gameRepository.saveAndFlush(game);
        return game;
    }

    public Game addHolesTravelled(Game game, int holesTravelled) {
        game.setHolesTravelled(game.getHolesTravelled() + holesTravelled);
        game = gameRepository.saveAndFlush(game);
        return game;
    }

    public Boolean checkCanUseCard(Game game, Card playedCard){
        Card lastCard = game.getLastCardPlayed();

        // Last move was by a different person, lastCardPlayed was reset after move
        if(lastCard == null) return true;
        // Trying to use different card from last move
        if(playedCard.getId() != null && !lastCard.getId().equals(playedCard.getId())){
            System.out.println(String.format("Cant use that card: LastCardPlayed = %s of %s, your card = %s of %s",
                                 lastCard.getRank(), lastCard.getSuit(), playedCard.getRank(), playedCard.getSuit()));
            return false;
        }
        // Card id not sent with request, need to check manually
        if(lastCard.getRank().equals(Rank.SEVEN) && playedCard.getRank().equals(Rank.SEVEN)){
            return true;
        }
        System.out.println("Can't use that card");
        return false;
    }

    private Boolean checkGameFull(Game game) {
        if(!game.isFull()){
            System.out.println("Game is not full yet");
            return false;
        }
        return true;
    }
}
