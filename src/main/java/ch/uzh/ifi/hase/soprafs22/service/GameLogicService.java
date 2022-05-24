package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.Color;
import ch.uzh.ifi.hase.soprafs22.constant.Rank;
import ch.uzh.ifi.hase.soprafs22.entity.Ball;
import ch.uzh.ifi.hase.soprafs22.entity.BoardState;
import ch.uzh.ifi.hase.soprafs22.entity.Game;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class GameLogicService {

    public Set<Integer> highlightBalls (Game game, Rank cardRank, Set<Ball> balls, Color playerColor, Color teamMateColor) {
        Set<Integer> highlightedBalls = new HashSet<>();

        for (Ball ball : balls) {

            Set<Integer> possibleMoves = getPossibleMoves(game, cardRank, balls, ball);
            Set<Integer> possibleDestinations = getPossibleDestinations(possibleMoves, ball, balls);

            int ballPos = ball.getPosition();
            Color ballColor = ball.getColor();
            if (ballColor.equals(playerColor)  || (cardRank.equals(Rank.SEVEN) && ballColor.equals(teamMateColor))) {
                if (!possibleDestinations.isEmpty()) {highlightedBalls.add(ballPos);}
            }
        }

        return highlightedBalls;
    }

    /**
     * 
     * @param game Needed for moves with a SEVEN
     * @param cardRank
     * @param balls
     * @param ball to move with
     * @return Set<Integer> of possible moves with ball relative to ball position
     */
    public static Set<Integer> getPossibleMoves(Game game, Rank cardRank, Set<Ball> balls, Ball ball) {
        Set<Integer> possibleMoves = new HashSet<Integer>();

        if (BoardState.normalCards.get(cardRank) != null) {
            possibleMoves.add(BoardState.normalCards.get(cardRank));
        }
        else if (cardRank.equals(Rank.FOUR)) {
            possibleMoves.add(4);
            possibleMoves.add(-4);
        }
       
        else if (cardRank.equals(Rank.SEVEN)) {
            int holesTraveled = game.getHolesTravelled();

            for(int i = 1; i <= 7 - holesTraveled; i++){
                possibleMoves.add(i);
            }
        }
        else if (cardRank.equals(Rank.KING)) {
            // MOVE BY 13 OR INVOKE A BALL FROM HOME

            if (checkCanGoOutOfHome(ball, balls) == 2) {possibleMoves.add(100);}
            else if (checkCanGoOutOfHome(ball, balls) == 1) {possibleMoves = Set.of();}
            else {possibleMoves = Set.of(13);}
        }
        else if (cardRank.equals(Rank.ACE)) {

            if (checkCanGoOutOfHome(ball, balls) == 2) {possibleMoves.add(100);}
            else if (checkCanGoOutOfHome(ball, balls) == 1) {possibleMoves = Set.of();}
            else {possibleMoves = Set.of(1,11);}
        }

        // CHECK WHETHER ANY BALL ON THE WAY ON ITS STARTING POSITION
        possibleMoves = checkBallOnTheWayOnStarting(ball, balls, possibleMoves);

        // IF BALL IN BASE EXCLUDE TOO LONG MOVES
        possibleMoves = excludeTooLongMoves(ball, possibleMoves);

       if (cardRank.equals(Rank.JACK)) {

            // Can't use JACK on balls in Home or Base
            if(ball.checkBallInBase() || ball.checkBallInHome()){
                return possibleMoves;
            }

            for (Ball b : balls) {
                if (canSwitchBallWithJack(ball, b, game)){

                    int possibleMove = b.getPosition() - ball.getPosition();
                    possibleMoves.add(possibleMove);

               }
           }
       }

        return possibleMoves;
    }

    public Set<Integer> getPossibleDestinations (Set<Integer> possibleMoves, Ball ball, Set<Ball> balls) {

        Set<Integer> possibleDestinations = new HashSet<>();

        // IF BALL IN HOME
        if (ball.checkBallInHome()) {

            for (int possibleMove : possibleMoves) {

                // CHECK IF BALL CAN GO OUT OF HOME
                if (possibleMove == 100) {
                    possibleDestinations = getStartPosition(ball);
                    return possibleDestinations;
                }
            }
        }

        // IF BALL IN BASE
        else if (ball.checkBallInBase()) {

            for (int possibleMove : possibleMoves) {

                addInBaseDestinations(ball, possibleDestinations, possibleMove);

            }
        }

        // IF BALL ON BOARD
        else {

            for (int possibleMove : possibleMoves) {

                // CHECK IF BALL CAN GO BASE; IF SO ADD ADEQUATE DESTINATION
                addToBaseDestinations(possibleMoves, ball, possibleDestinations, possibleMove);

                // IF BALL ALREADY IN BASE DISABLE GOING BACK ONTO BOARD
                addOnBoardDestinations(ball, possibleDestinations, possibleMove);

            }
        }

        // DELETE DESTINATIONS OCCUPIED BY SAME COLOR BALLS
        Set<Integer> toBeRemoved = new HashSet<>();
        Set<Integer> newDestinations = possibleDestinations;
        for (int possibleDestination : possibleDestinations) {
            if (checkSameColorBallOnDestination(ball, balls, possibleDestination)) {toBeRemoved.add(possibleDestination);}
        }
        for(int i : toBeRemoved) {newDestinations.remove(i);}

        newDestinations = checkBallOnTheWayInBase(ball, balls, newDestinations);

        return newDestinations;
    }

    private void addOnBoardDestinations(Ball ball, Set<Integer> possibleDestinations, int possibleMove) {
        if (!ball.checkBallInBase()) {

            // ADD POSSIBLE ON BOARD MOVES i.e. not into base/from home
            // modulo div as board's last pos is 63
            if (!((ball.getPosition() + possibleMove) < 0)) {
                possibleDestinations.add((ball.getPosition() + possibleMove) % 64);
            }
            else {
                possibleDestinations.add((ball.getPosition() + possibleMove) + 64);
            }

        }
    }

    private void addToBaseDestinations(Set<Integer> possibleMoves, Ball ball, Set<Integer> possibleDestinations, int possibleMove) {
        Color color;
        Integer ballPos;
        if (checkCanGoBase(color = ball.getColor(), ballPos = ball.getPosition(), possibleMoves)) {

            int baseMove = ballPos + possibleMove;

            if (color.equals(Color.GREEN)) {

                if (!BoardState.basePoints.contains(ballPos)) {
                    int greenMove = baseMove - 1;
                    if (greenMove <= 67 && greenMove >= 64) {
                        possibleDestinations.add(greenMove);
                    }

                }
                else {
                    if (baseMove <= 67 && baseMove >= 64 ) {
                        possibleDestinations.add(baseMove);
                    }
                }

            }
            else if (color.equals(Color.RED)) {
                baseMove = ballPos + possibleMove + 51;
                if (baseMove <= 71 && baseMove >= 68 && ballPos < 16) {
                    possibleDestinations.add(baseMove);
                }
            }
            else if (color.equals(Color.YELLOW)) {
                baseMove = ballPos + possibleMove + 39;
                if (baseMove <= 75 && baseMove >= 72 && ballPos < 32) {
                    possibleDestinations.add(baseMove);
                }
            }
            else {
                baseMove = ballPos + possibleMove + 27;
                if (baseMove <= 79 && baseMove >= 76 && ballPos < 48) {
                    possibleDestinations.add(baseMove);
                }
            }
        }
    }

    private void addInBaseDestinations(Ball ball, Set<Integer> possibleDestinations, int possibleMove) {

        int inBaseMove = ball.getPosition() + possibleMove;

        possibleDestinations.add(inBaseMove);
    }

    public static List<Integer> getHolesTravelled(int destination, int ballPosition, Boolean withDestination) {
        System.out.println("moving from " + ballPosition + " to " + destination);
        List<Integer> holesTraveled = new ArrayList<>();

        int moveLength = destination - ballPosition;
        if (moveLength == 60 && !BoardState.homePoints.contains(destination)) {moveLength = -4;}

        if(BoardState.homePoints.contains(ballPosition)){
            holesTraveled.add(ballPosition);
            holesTraveled.add(destination);
            return holesTraveled;
        }

        if (!withDestination) {destination--;}

            // WHEN MOVING WITH 4
            if (moveLength == -4) {
                if (destination < ballPosition) {
                    for (int i = ballPosition; i >= destination; i--) {
                        holesTraveled.add(i);
                    }
                }
                else {
                    for (int i = ballPosition; i >= 0; i--) {
                        holesTraveled.add(i);
                    }
                    for (int j = 63; j >= destination; j--) {
                        holesTraveled.add(j);
                    }
                }
            }
            // WHEN CROSSING "THE END" OF THE BOARD
            else if (moveLength < 0) {
                for (int i = ballPosition; i <= destination + 64; i++) {
                    holesTraveled.add(i);
                }
                holesTraveled.replaceAll(e -> e % 64);
            }
            // WHEN MOVING AROUND BOARD AND IN THE BASE
            else if ((moveLength <= 13) || BoardState.basePoints.contains(destination) && BoardState.basePoints.contains(ballPosition)) {
                for (int i = ballPosition; i <= destination; i++) {
                    holesTraveled.add(i);
                }
            }
            // WHEN GOING INTO BASE FROM BOARD
            else if (ballPosition < 64){
                // TO GREEN BASE
                if (64 <= destination && destination <= 67) {

                        for (int i = ballPosition; i <= 63; i++) {
                            holesTraveled.add(i);
                        }

                        holesTraveled.add(0);

                        for (int j = 64; j <= destination; j++) {
                            holesTraveled.add(j);
                        }

                }
                // TO RED
                else if (68 <= destination && destination <= 71) {

                    for (int i = ballPosition; i <= 16; i++) {
                        holesTraveled.add(i);
                    }

                    for (int j = 68; j <= destination; j++) {
                        holesTraveled.add(j);
                    }

                }
                // TO YELLOW
                else if (72 <= destination && destination <= 75) {

                    for (int i = ballPosition; i <= 32; i++) {
                        holesTraveled.add(i);
                    }

                    for (int j = 72; j <= destination; j++) {
                        holesTraveled.add(j);
                    }

                }
                // TO BLUE
                else {

                    for (int i = ballPosition; i <= 48; i++) {
                        holesTraveled.add(i);
                    }

                    for (int j = 76; j <= destination; j++) {
                        holesTraveled.add(j);
                    }

                }
            }

        return holesTraveled;
    }

    public static Integer ballBackToHome(Ball ball, Set<Ball> balls) {

        if (ball.getColor().equals(Color.GREEN)) {
            ball.setPosition(getFreeHomeHoles(Color.GREEN, balls).stream().findAny().get());
        }
        else if (ball.getColor().equals(Color.RED)) {
            ball.setPosition(getFreeHomeHoles(Color.RED, balls).stream().findAny().get());
        }
        else if (ball.getColor().equals(Color.YELLOW)) {
            ball.setPosition(getFreeHomeHoles(Color.YELLOW, balls).stream().findAny().get());
        }
        else {
            ball.setPosition(getFreeHomeHoles(Color.BLUE, balls).stream().findAny().get());
        }

        return ball.getPosition();
    }

    public static Set<Integer> getFreeHomeHoles(Color color, Set<Ball> balls) {

        if (color == Color.GREEN) {
            Set<Integer> freeHomeHoles = new HashSet<>(Set.of(80,81,82,83));
            for (Ball b : balls) {
                freeHomeHoles.remove(b.getPosition());
            }
            return freeHomeHoles;
        }
        else if (color == Color.RED) {
            Set<Integer> freeHomeHoles = new HashSet<>(Set.of(84,85,86,87));
            for (Ball b : balls) {
                freeHomeHoles.remove(b.getPosition());
            }
            return freeHomeHoles;
        }
        else if (color == Color.YELLOW) {
            Set<Integer> freeHomeHoles = new HashSet<>(Set.of(88,89,90,91));
            for (Ball b : balls) {
                freeHomeHoles.remove(b.getPosition());
            }
            return freeHomeHoles;
        }
        else {
            Set<Integer> freeHomeHoles = new HashSet<>(Set.of(92,93,94,95));
            for (Ball b : balls) {
                freeHomeHoles.remove(b.getPosition());
            }
            return freeHomeHoles;
        }
    }

    public static Set<Integer> getStartPosition(Ball ball) {

        if (ball.getColor().equals(Color.GREEN)) {
            return Set.of(0);
        }
        else if (ball.getColor().equals(Color.RED)) {
            return Set.of(16);
        }
        else if (ball.getColor().equals(Color.YELLOW)) {
            return Set.of(32);
        }
        else {
            return Set.of(48);
        }
    }


    public static int getBaseEndPosition(Ball ball) {

        if (ball.getColor().equals(Color.GREEN)) {
            return 67;
        }
        else if (ball.getColor().equals(Color.RED)) {
            return 71;
        }
        else if (ball.getColor().equals(Color.YELLOW)) {
            return 75;
        }
        else {
            return 79;
        }
    }

    public Boolean checkIfOnLastBasePosition(int ballPosition) {
        List<Integer> lastPositions = new ArrayList<>(List.of(67,71,75,79));
        return lastPositions.contains(ballPosition);
    }

    public static Set<Integer> excludeTooLongMoves (Ball ball, Set<Integer> possibleMoves) {

        Set<Integer> strippedMoves = new HashSet<>(possibleMoves);

        if (ball.checkBallInBase() && !possibleMoves.isEmpty()) {

            Set<Integer> toBeRemoved = new HashSet<>();

            int maxMove = maximumMoveInBase(ball);

            for (Integer move : strippedMoves) {
                if (move > maxMove || move == -4) {toBeRemoved.add(move);}
            }

            for (int i : toBeRemoved) {
                strippedMoves.remove(i);
            }
        }

        return strippedMoves;
    }

    public static int maximumMoveInBase(Ball ball) {
        return getBaseEndPosition(ball) - ball.getPosition();
    }

    public static Set<Integer> checkBallOnTheWayOnStarting(Ball ball, Set<Ball> balls, Set<Integer> possibleMoves) {

        int startPos = ball.getPosition();

        // for every possible move, we check if any ball on the way is on its starting point
        Set<Integer> tempMoves = new HashSet<>(possibleMoves);
        Set<Integer> toBeRemoved = new HashSet<>();
        for (Ball b : balls) {

            int ballPos = b.getPosition();

            // IF BALL ON ONE OF THE STARTING POSITIONS
            if (getStartPosition(b).contains(ballPos)) {

                // CHECK FOR EVERY POSSIBLE MOVE
                for (int possibleMove : tempMoves) {

                    if (possibleMove == 100) {return possibleMoves;}

                    // IF HOLES ON THE WAY CONTAIN THIS BALL
                    int destination = possibleMove + startPos;
                    if (ball.getColor() != Color.GREEN
                            && getHolesTravelled(destination%64, startPos, true).stream().skip(1).anyMatch(x -> x == ballPos)) {
                            toBeRemoved.add(possibleMove);
                    }
                    else if (getHolesTravelled(destination%64, startPos, true).stream().skip(1).anyMatch(x -> x == ballPos)) {
                        toBeRemoved.add(possibleMove);
                    }
                }
            }
        }

        for (int i : toBeRemoved) {
            tempMoves.remove(i);
        }

        return tempMoves;
    }

    public Boolean checkSameColorBallOnDestination(Ball ball, Set<Ball> balls, int destination) {

        for (Ball b : balls) {
            if (b.getPosition() == destination && b.getColor().equals(ball.getColor())) {return true;}
        }
        return false;
    }

    public Set<Integer> checkBallOnTheWayInBase(Ball ball, Set<Ball> balls, Set<Integer> possibleDestinations) {

            Color color = ball.getColor();

            Set<Integer> tempDestinations = new HashSet<>(possibleDestinations);
            Set<Integer> toBeRemoved = new HashSet<>();

            for (Ball b: balls) {
                int pos = b.getPosition();
                if (b.getColor() == color && b.checkBallInBase() && ball != b) {
                    for (int destination : tempDestinations) {
                        if (destination >= pos) {
                            toBeRemoved.add(destination);
                        }
                    }
                }

            }

            for (int i : toBeRemoved) {
                tempDestinations.remove(i);
            }

            return tempDestinations;

    }

    // Compensate for the offset in the hole number by adding specific value
    public Boolean checkCanGoBase (Color color, int position, Set<Integer> possibleMoves) {

        if (BoardState.startingPoints.contains(position)) {return false;}

        if (color.equals(Color.GREEN)) {
            for (int possibleMove : possibleMoves) {
                if (position + possibleMove <= 68) {
                    return true;
                }
            }
        }
        else if (color.equals(Color.RED)) {
            for (int possibleMove : possibleMoves) {
                if ((position + 51 + possibleMove <= 71)) {
                    return true;
                }
            }
        }
        else if (color.equals(Color.YELLOW)) {
            for (int possibleMove : possibleMoves) {
                if (position + 39 + possibleMove <= 75) {
                    return true;
                }
            }
        }
        else if (color.equals(Color.BLUE)) {
            for (int possibleMove : possibleMoves) {
                if (position + 27 + possibleMove <= 79) {
                    return true;
                }
            }
        }
        return false;
    }

    // RETURNS:
    // 2 IF CAN GO OUT OF HOME,
    // 1 IF START IS OCCUPIED,
    // 0 IF BALL NOT IN HOME
    public static int checkCanGoOutOfHome (Ball ball, Set<Ball> balls) {

        Color color = ball.getColor();

        if (!BoardState.homePoints.contains(ball.getPosition())) {
            return 0;
        }

        for (Ball b : balls) {
            if (color == b.getColor()) {
                if (color == Color.GREEN) {
                    if (b.getPosition().equals(0)) {
                        return 1;
                    }
                }
                else if (color == Color.RED) {
                    if (b.getPosition().equals(16)) {
                        return 1;
                    }
                }
                else if (color == Color.YELLOW) {
                    if (b.getPosition().equals(32)) {
                        return 1;
                    }
                }
                else if (color == Color.BLUE) {
                    if (b.getPosition().equals(48)) {
                        return 1;
                    }
                }
            }
        }
        return 2;

    }

    // Account for the case when move is made with 7 => player gets access to teammate's balls
    public List<Ball> getPlayerBalls (List<Ball> balls, List<Color> playerColors) {

        List<Ball> playerBalls = new ArrayList<>();

        for (Color color : playerColors) {
            for (Ball ball : balls) {
                if (ball.getColor().equals(color)) {
                    playerBalls.add(ball);
                }
            }
        }

        return playerBalls;
    }

    public static void swapBalls(Ball ball, Ball targetBall, Set<Ball> balls) {
        Integer oldPosition = ball.getPosition();
        Integer targetPosition = targetBall.getPosition();

        ball.setPosition(targetPosition);
        targetBall.setPosition(oldPosition);
        
        System.out.println("Balls at positions " + oldPosition + " and " + targetPosition + " switched");

    }

    private static boolean canSwitchBallWithJack(Ball ball, Ball targetBall, Game game) {
        Color userColor = ball.getColor();
        int targetBallPos = targetBall.getPosition();
        // cant move with your own balls
        if(targetBall.getColor().equals(userColor)) return false;

        // Can't swap with ball that is in start position
        if(getStartPosition(targetBall).contains(targetBall.getPosition())){
            return false;
        }
        // Can't swap with ball that is in base or home
        if(targetBallPos < 0 || targetBallPos > 63) return false;

        // Cant swap ball with itself
        if(targetBall.equals(ball)) return false;

        return true;
    }


}
