package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.Color;
import ch.uzh.ifi.hase.soprafs22.constant.Rank;
import ch.uzh.ifi.hase.soprafs22.entity.Ball;
import ch.uzh.ifi.hase.soprafs22.entity.Game;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameLogicServerTest {
    @Mock
    private GameLogicService gameLogicService = new GameLogicService();

    @InjectMocks
    private final Ball green1 = new Ball(Color.GREEN, 1);
    private final Ball green0 = new Ball(Color.GREEN, 0);
    private final Ball green14 = new Ball(Color.GREEN, 14);
    private final Ball green15 = new Ball(Color.GREEN, 14);
    private final Ball green16 = new Ball(Color.GREEN, 16);
    private final Ball green80 = new Ball(Color.GREEN, 80);
    private final Ball green81 = new Ball(Color.GREEN, 81);
    private final Ball green82 = new Ball(Color.GREEN, 82);
    private final Ball green83 = new Ball(Color.GREEN, 83);

    private final Ball green63 = new Ball(Color.GREEN, 63);
    private final Ball green64 = new Ball(Color.GREEN, 64);
    private final Ball green65 = new Ball(Color.GREEN, 65);
    private final Ball green66 = new Ball(Color.GREEN, 66);
    private final Ball green67 = new Ball(Color.GREEN, 67);

    private final Ball red84 = new Ball(Color.RED, 84);
    private final Ball red85 = new Ball(Color.RED, 85);
    private final Ball red86 = new Ball(Color.RED, 86);
    private final Ball red68 = new Ball(Color.RED, 68);
    private final Ball red1 = new Ball(Color.RED, 1);
    private final Ball red15 = new Ball(Color.RED, 15);
    private final Ball red16 = new Ball(Color.RED, 16);
    private final Ball red17 = new Ball(Color.RED, 17);
    private final Ball red31 = new Ball(Color.RED, 31);

    private final Ball yellow88 = new Ball(Color.YELLOW, 88);
    private final Ball yellow89 = new Ball(Color.YELLOW, 89);
    private final Ball yellow91 = new Ball(Color.YELLOW, 91);
    private final Ball yellow72 = new Ball(Color.YELLOW, 72);
    private final Ball yellow31 = new Ball(Color.YELLOW, 31);
    private final Ball yellow32 = new Ball(Color.YELLOW, 32);
    private final Ball yellow33 = new Ball(Color.YELLOW, 33);
    private final Ball yellow47 = new Ball(Color.YELLOW, 47);

    private final Ball blue47 = new Ball(Color.BLUE, 47);
    private final Ball blue48 = new Ball(Color.BLUE, 48);
    private final Ball blue49 = new Ball(Color.BLUE, 49);
    private final Ball blue63 = new Ball(Color.BLUE, 63);
    private final Ball blue92 = new Ball(Color.BLUE, 92);
    private final Ball blue93 = new Ball(Color.BLUE, 93);
    private final Ball blue94 = new Ball(Color.BLUE, 94);
    private final Ball blue95 = new Ball(Color.BLUE, 95);
    private final Ball blue76 = new Ball(Color.BLUE, 76);

    @Test
    public void cardChosen_validBalls() {
        Rank cardRank = Rank.ACE;
        Color playerColor = Color.GREEN;
        Color teammateColor = Color.BLUE;

        Set<Ball> balls = new HashSet<>(Set.of(green1, green0, green64, green14, green16, green63, green67));

        assertEquals(Set.of(1, 0, 64, 14, 16), gameLogicService.highlightBalls(new Game(), cardRank, balls, playerColor, teammateColor));
    }

    @Test
    public void ballChosen_validPossibleMoves() {
        Rank cardRank1 = Rank.ACE;
        Rank cardRank2 = Rank.FOUR;

        Set<Ball> balls = new HashSet<>(Set.of(green1, green0, green64, green14, green16, green63));

        // Game can be null because its only used for moves with a SEVEN
        Set<Integer> possibleMoves1 = GameLogicService.getPossibleMoves(null, cardRank1, balls, green0);
        Set<Integer> possibleMoves2 = GameLogicService.getPossibleMoves(null, cardRank2, balls, green0);

        assertEquals(Set.of(1,11), possibleMoves1);
        assertEquals(Set.of(4,-4), possibleMoves2);

    }

    @Test
    public void ballChosen_checkBallOnTheWayOnStarting_validPossibleMoves() {

        Set<Ball> balls = new HashSet<>(Set.of(green1, green0, green63, green14, red16));

        Set<Integer> testPossibleMoves = new HashSet<>(Set.of(1,11));

        assertEquals(Set.of(1), GameLogicService.checkBallOnTheWayOnStarting(green14, balls, testPossibleMoves));
        assertEquals(Set.of(), GameLogicService.checkBallOnTheWayOnStarting(green63, balls, testPossibleMoves));

    }

    @Test
    public void checkBallOnTheWayInBaseTest() {

        Set<Ball> balls = new HashSet<>(Set.of(green64, green66));

        Set<Integer> testPossibleDestinations1 = new HashSet<>(Set.of(65,66,67));

        assertEquals(Set.of(65), gameLogicService.checkBallOnTheWayInBase(green64, balls, testPossibleDestinations1));

    }

    @Test
    public void ballChosen_validPossibleDestinations() {
    // GOING INTO BASE WITH DIFFERENT COLORS
        Set<Integer> possibleDestinationsGREEN1 = gameLogicService.getPossibleDestinations(Set.of(5), green63, Set.of());
        Set<Integer> possibleDestinationsGREENinBase = gameLogicService.getPossibleDestinations(Set.of(1,2,3), green64, Set.of());

        Set<Integer> possibleDestinationsRED1 = gameLogicService.getPossibleDestinations(Set.of(5), red15, Set.of());
        Set<Integer> possibleDestinationsYELLOW1 = gameLogicService.getPossibleDestinations(Set.of(5), yellow31, Set.of());
        Set<Integer> possibleDestinationsBLUE1 = gameLogicService.getPossibleDestinations(Set.of(5), blue47, Set.of());

        assertEquals(Set.of(4,67), possibleDestinationsGREEN1);
        assertEquals(Set.of(20,71), possibleDestinationsRED1);
        assertEquals(Set.of(36,75), possibleDestinationsYELLOW1);
        assertEquals(Set.of(79,52), possibleDestinationsBLUE1);
        assertEquals(Set.of(65,66,67), possibleDestinationsGREENinBase);

        Set<Integer> possibleDestinations1 = gameLogicService.getPossibleDestinations(Set.of(1, 11), green0, Set.of());
    Set<Integer> possibleDestinations2 = gameLogicService.getPossibleDestinations(Set.of(1, 11), green63, Set.of());

    Set<Integer> possibleDestinationsGREEN = gameLogicService.getPossibleDestinations(Set.of(1, 11, 100), green80, Set.of());
    Set<Integer> possibleDestinationsRED = gameLogicService.getPossibleDestinations(Set.of(1, 11, 100), red84, Set.of());
    Set<Integer> possibleDestinationsYELLOW = gameLogicService.getPossibleDestinations(Set.of(1, 11, 100), yellow88, Set.of());
    Set<Integer> possibleDestinationsBLUE = gameLogicService.getPossibleDestinations(Set.of(1, 11, 100), blue95, Set.of());

    Set<Integer> possibleDestinationsGREEN4 = gameLogicService.getPossibleDestinations(Set.of(-4, 4), green0, Set.of());
    Set<Integer> possibleDestinationsRED4 = gameLogicService.getPossibleDestinations(Set.of(-4, 4), red16, Set.of());
    Set<Integer> possibleDestinationsYELLOW4 = gameLogicService.getPossibleDestinations(Set.of(-4, 4), yellow32, Set.of());
    Set<Integer> possibleDestinationsBLUE4 = gameLogicService.getPossibleDestinations(Set.of(-4, 4), blue48, Set.of());


    assertEquals(Set.of(1,11), possibleDestinations1);
    assertEquals(Set.of(0,10), possibleDestinations2);

    assertEquals(Set.of(4,60), possibleDestinationsGREEN4);
    assertEquals(Set.of(20,12), possibleDestinationsRED4);
    assertEquals(Set.of(36,28), possibleDestinationsYELLOW4);
    assertEquals(Set.of(52,44), possibleDestinationsBLUE4);

    assertEquals(Set.of(0), possibleDestinationsGREEN);
    assertEquals(Set.of(16), possibleDestinationsRED);
    assertEquals(Set.of(32), possibleDestinationsYELLOW);
    assertEquals(Set.of(48), possibleDestinationsBLUE);
    }

    @Test
    public void checkCanGoOutOfHomeTest() {

        Set<Ball> balls = new HashSet<>(Set.of(green1, green0, green64, green14, green16, green63, green80, red84, yellow88, blue95));

        assertEquals(0, GameLogicService.checkCanGoOutOfHome(green0, balls));
        assertEquals(1, GameLogicService.checkCanGoOutOfHome(green80, balls));
        assertEquals(2, GameLogicService.checkCanGoOutOfHome(red84, balls));
        assertEquals(2, GameLogicService.checkCanGoOutOfHome(yellow88, balls));
        assertEquals(2, GameLogicService.checkCanGoOutOfHome(blue95, balls));
    }

    @Test
    public void getHolesTravelledTest() {

        List<Integer> from80to0 = GameLogicService.getHolesTravelled(0,80,true);
        List<Integer> from84to16 = GameLogicService.getHolesTravelled(16,84,true);
        List<Integer> from88to32 = GameLogicService.getHolesTravelled(32,88,true);
        List<Integer> from92to48 = GameLogicService.getHolesTravelled(48,92,true);

        List<Integer> from64to65 = GameLogicService.getHolesTravelled(65,64,true);
        List<Integer> from68to69 = GameLogicService.getHolesTravelled(69,68,true);
        List<Integer> from72to73 = GameLogicService.getHolesTravelled(73,72,true);
        List<Integer> from76to77 = GameLogicService.getHolesTravelled(77,76,true);

        List<Integer> from0to6 = GameLogicService.getHolesTravelled(6,0,true);
        List<Integer> from62to4 = GameLogicService.getHolesTravelled(4,62,true);
        List<Integer> from4to0 = GameLogicService.getHolesTravelled(0,4,true);
        List<Integer> from0to60 = GameLogicService.getHolesTravelled(60,0,true);
        List<Integer> from1to61 = GameLogicService.getHolesTravelled(61,1,true);
        List<Integer> from2to62 = GameLogicService.getHolesTravelled(62,2,true);
        List<Integer> from3to63 = GameLogicService.getHolesTravelled(63,3,true);
        List<Integer> from63to0 = GameLogicService.getHolesTravelled(0,63,true);
        List<Integer> from14to15 = GameLogicService.getHolesTravelled(15,14,true);
        List<Integer> from14to25 = GameLogicService.getHolesTravelled(25,14,true);

        assertEquals(List.of(80,0), from80to0);
        assertEquals(List.of(84,16), from84to16);
        assertEquals(List.of(88,32), from88to32);
        assertEquals(List.of(92,48), from92to48);

        assertEquals(List.of(64,65), from64to65);
        assertEquals(List.of(68,69), from68to69);
        assertEquals(List.of(72,73), from72to73);
        assertEquals(List.of(76,77), from76to77);

        assertEquals(List.of(0,1,2,3,4,5,6), from0to6);
        assertEquals(List.of(62,63,0,1,2,3,4), from62to4);
        assertEquals(List.of(4,3,2,1,0), from4to0);
        assertEquals(List.of(0,63,62,61,60), from0to60);
        assertEquals(List.of(1,0,63,62,61), from1to61);
        assertEquals(List.of(2,1,0,63,62), from2to62);
        assertEquals(List.of(3,2,1,0,63), from3to63);
        assertEquals(List.of(63,0), from63to0);
        assertEquals(List.of(14,15), from14to15);
        assertEquals(List.of(14,15,16,17,18,19,20,21,22,23,24,25), from14to25);

    }

    @Test
    public void excludeTooLongMovesTest() {

        Set <Integer> possibleMoves = new HashSet<>(Set.of(-4,1,2,3,4,5,6,7,8,9,10,11,12,13));

        assertEquals(Set.of(1,2,3), GameLogicService.excludeTooLongMoves(green64, possibleMoves));
        assertEquals(Set.of(1,2,3), GameLogicService.excludeTooLongMoves(red68, possibleMoves));
        assertEquals(Set.of(1,2,3), GameLogicService.excludeTooLongMoves(yellow72, possibleMoves));
        assertEquals(Set.of(1,2,3), GameLogicService.excludeTooLongMoves(blue76, possibleMoves));

    }

    @Test
    public void getFreeHomeHolesTest() {

        Set<Ball> balls = new HashSet<Ball>(Set.of(green81,green82,green83,
                red84,red85,red86,
                yellow88,yellow89,yellow91,
                blue92,blue93,blue94));

        Set<Ball> baseFull = new HashSet<Ball>(Set.of(green80,green81,green82,green83));

        assertEquals(Set.of(80), GameLogicService.getFreeHomeHoles(Color.GREEN, balls));
        assertEquals(Set.of(87), GameLogicService.getFreeHomeHoles(Color.RED, balls));
        assertEquals(Set.of(90), GameLogicService.getFreeHomeHoles(Color.YELLOW, balls));
        assertEquals(Set.of(95), GameLogicService.getFreeHomeHoles(Color.BLUE, balls));

        assertEquals(Set.of(), GameLogicService.getFreeHomeHoles(Color.GREEN, baseFull));

    }

    @Test
    public void ballBackToHomeTest() {

        Set<Ball> balls = new HashSet<>(Set.of(green1,green81,green82,green83,
                red17,red84,red85,red86,
                yellow33,yellow88,yellow89,yellow91,
                blue49,blue92,blue93,blue94));

        GameLogicService.ballBackToHome(green1, balls);
        GameLogicService.ballBackToHome(red17, balls);
        GameLogicService.ballBackToHome(yellow33, balls);
        GameLogicService.ballBackToHome(blue49, balls);

        assertEquals(80, green1.getPosition());
        assertEquals(87, red17.getPosition());
        assertEquals(90, yellow33.getPosition());
        assertEquals(95, blue49.getPosition());

    }

//    @Test
//    public void jackChosen_ProperDestinations() {
//
//        Set<Ball> balls = new HashSet<>(Set.of(green0,green14,green64,green81,
//                red1,red16,red17,red68,red85,
//                yellow32,yellow33,yellow72,yellow88,
//                blue48,blue49,blue76,blue92));
//
//        Set<Integer> possibleMoves = gameLogicService.getPossibleMoves(Rank.JACK, balls, green14);
//        Set<Integer> possibleDestinations = gameLogicService.getPossibleDestinations(possibleMoves, green14, balls);
//
//        assertEquals(Set.of(1,17,33,49), possibleDestinations);
//
//    }

}
