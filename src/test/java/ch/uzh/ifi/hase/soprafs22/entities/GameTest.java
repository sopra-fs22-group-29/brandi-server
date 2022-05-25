package ch.uzh.ifi.hase.soprafs22.entities;

import ch.uzh.ifi.hase.soprafs22.constant.Color;
import ch.uzh.ifi.hase.soprafs22.constant.Rank;
import ch.uzh.ifi.hase.soprafs22.constant.Suit;
import ch.uzh.ifi.hase.soprafs22.entity.*;
import ch.uzh.ifi.hase.soprafs22.entity.websocket.Move;
import ch.uzh.ifi.hase.soprafs22.service.GameLogicService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Mock
    private GameLogicService gameLogicService = new GameLogicService();
    
    private static User player1;
    private static User player2;
    private static User player3;
    private static User player4;
    private static Game game;

    private static Hashtable<Color, List<Integer>> positionDict;

    private static List<Ball> balls;
    private static Set<Ball> ballsSet;
    private static Card card;

    @BeforeAll
    public static void init() {
        // GIVEN
        player1 = new User("username", "password");
        player2 = new User("username2", "password2");
        player3 = new User("username3", "password3");
        player4 = new User("username4", "password4");
        game = new Game(player1);

        List<User> users = List.of(player2, player3, player4);
        users.forEach(user -> game.addPlayer(user));

        positionDict = new Hashtable<>();

        positionDict.put(Color.GREEN, new ArrayList<>(Arrays.asList(60, 61, 62, 63)));
        positionDict.put(Color.RED, new ArrayList<>(Arrays.asList(12, 13, 14, 15)));
        positionDict.put(Color.YELLOW, new ArrayList<>(Arrays.asList(28, 29, 30, 31)));
        positionDict.put(Color.BLUE, new ArrayList<>(Arrays.asList(44, 45, 46, 47)));

        
        card = new Card(Rank.FOUR, Suit.CLUB);
    }

    @BeforeEach
    void beforeEach() {
        // (Re)Set balls to state close to winning
        balls = new ArrayList<>();
        Long counter = 0L;
        for(Color color: Color.values()){
            for(int i = 3; i >= 0; i--){
                Ball ball = new Ball(color, positionDict.get(color).get(i));
                ball.setId(counter++);
                balls.add(ball);
            }
        }

        ballsSet = new HashSet<>();
        ballsSet.addAll(balls);

        game.setGameOver(false);
        game.startNewRound();
        game.getBoardstate().setBalls(ballsSet);
    }

    @Test
    public void testMovesIntoBaseWithEveryColor() {
        // WHEN

        for(Ball ball: balls){
            // Overwrite game.gameover to continue making moves and testing them
            game.setGameOver(false);
            assertMoveIntoBasePossibleAndMakeMove(ball);
        }

        // THEN
        assertTrue(game.getGameOver());
    }

    @Test
    public void testGameWonAfterAllMarblesInBase() {
        Integer marblesMovedIntoBase = 0;
        for(Ball ball: balls){
            if(ball.getColor().equals(Color.GREEN) || ball.getColor().equals(Color.YELLOW)){
                // Ignore balls of one team
                continue;
            }

            if(marblesMovedIntoBase <= 7){
                assertFalse(game.getGameOver());
            }

            assertMoveIntoBasePossibleAndMakeMove(ball);
            marblesMovedIntoBase++;

            if(marblesMovedIntoBase.equals(8)){
                System.out.println("Game over");
                assertTrue(game.getGameOver());
            }

        }
    }

    @Test
    public void givenGameThatIsOver_whenMakingMove_thenFalseReturned() {
        game.setGameOver(true);
        assertFalse(game.makeMove(new Move()));
    }

    @Test
    public void givenGame_whenEliminatingBall_thenBallEliminated() {
        BoardState boardState = game.getBoardstate();
        // GIVEN: Game with ball that can eliminate other marble using move
        Ball moveBall = new Ball(Color.BLUE, 56);
        moveBall.setId(99L);
        ballsSet.add(moveBall);
        boardState.setBalls(ballsSet);

        Integer destTile = 60;
        Move move = new Move(card, moveBall.getId(), destTile);

        Ball targetBall = boardState.getBallByPosition(destTile);
        assertNotNull(targetBall);

        // WHEN: Move is made
        game.makeMove(move);

        // THEN: marble at destTile is sent back to home
        assertEquals(moveBall, boardState.getBallByPosition(destTile));
        assertTrue(BoardState.homePoints.contains(targetBall.getPosition()));
    }

    /* @Test
    public void given_when_then() {
        
    } */


    private void assertMoveIntoBasePossibleAndMakeMove(Ball ball) {
        Move move = new Move();
        move.setBallId(ball.getId());
        Set<Integer> possibleMoves = GameLogicService.getPossibleMoves(game, Rank.FIVE, ballsSet, ball);
        List<Integer> possibleDestinations = List.copyOf(gameLogicService.getPossibleDestinations(possibleMoves, ball, ballsSet));

        Integer destTile = Collections.max(possibleDestinations);
        assertTrue(BoardState.basePoints.contains(destTile));

        move.setDestinationTile(destTile);
        move.setPlayedCard(new Card(Rank.FIVE, Suit.CLUB));

        game.makeMove(move);
    }

    

}
