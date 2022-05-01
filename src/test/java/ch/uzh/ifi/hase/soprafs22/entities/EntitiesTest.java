package ch.uzh.ifi.hase.soprafs22.entities;

import ch.uzh.ifi.hase.soprafs22.constant.Color;
import ch.uzh.ifi.hase.soprafs22.entity.Ball;
import ch.uzh.ifi.hase.soprafs22.entity.BoardState;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class EntitiesTest {

    @Test
    public void BallTest() {

        Ball ball = new Ball(Color.GREEN, 0);

        assertEquals(0, ball.getPosition());
        ball.setPosition(1);
        assertEquals(1, ball.getPosition());

        assertEquals(null, ball.getId());
        assertEquals(Color.GREEN, ball.getColor());

    }

    @Test
    public void BoardStateTest() {

        Ball ball = new Ball(Color.GREEN, 0);

        Set<Ball> balls = new HashSet<>(Set.of(ball));

        BoardState boardState = new BoardState(balls);

        assertEquals(balls, boardState.getBalls());
        //assertEquals(null, boardState.getBallById(null));

    }

}
