package ch.uzh.ifi.hase.soprafs22.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import ch.uzh.ifi.hase.soprafs22.constant.Color;
import ch.uzh.ifi.hase.soprafs22.constant.Rank;
import ch.uzh.ifi.hase.soprafs22.entity.Ball;
import ch.uzh.ifi.hase.soprafs22.entity.Game;

public class GameLogicServerTest {
    @Mock
    private GameLogicService gameLogicService = new GameLogicService();

    @InjectMocks
    private final Ball green0 = new Ball(Color.GREEN, 0);
    private final Ball green1 = new Ball(Color.GREEN, 1);
    private final Ball green2 = new Ball(Color.GREEN, 2);
    private final Ball green3 = new Ball(Color.GREEN, 3);
    private final Ball green4 = new Ball(Color.GREEN, 4);
    private final Ball green5 = new Ball(Color.GREEN, 5);
    private final Ball green6 = new Ball(Color.GREEN, 6);
    private final Ball green7 = new Ball(Color.GREEN, 7);
    private final Ball green8 = new Ball(Color.GREEN, 8);
    private final Ball green9 = new Ball(Color.GREEN, 9);
    private final Ball green10 = new Ball(Color.GREEN, 10);
    private final Ball green11 = new Ball(Color.GREEN, 11);
    private final Ball green12 = new Ball(Color.GREEN, 12);
    private final Ball green13 = new Ball(Color.GREEN, 13);
    private final Ball green14 = new Ball(Color.GREEN, 14);
    private final Ball green15 = new Ball(Color.GREEN, 15);
    private final Ball green16 = new Ball(Color.GREEN, 16);
    private final Ball green17 = new Ball(Color.GREEN, 17);
    private final Ball green18 = new Ball(Color.GREEN, 18);
    private final Ball green19 = new Ball(Color.GREEN, 19);
    private final Ball green20 = new Ball(Color.GREEN, 20);
    private final Ball green21 = new Ball(Color.GREEN, 21);
    private final Ball green22 = new Ball(Color.GREEN, 22);
    private final Ball green23 = new Ball(Color.GREEN, 23);
    private final Ball green24 = new Ball(Color.GREEN, 24);
    private final Ball green25 = new Ball(Color.GREEN, 25);
    private final Ball green26 = new Ball(Color.GREEN, 26);
    private final Ball green27 = new Ball(Color.GREEN, 27);
    private final Ball green28 = new Ball(Color.GREEN, 28);
    private final Ball green29 = new Ball(Color.GREEN, 29);
    private final Ball green30 = new Ball(Color.GREEN, 30);
    private final Ball green31 = new Ball(Color.GREEN, 31);
    private final Ball green32 = new Ball(Color.GREEN, 32);
    private final Ball green33 = new Ball(Color.GREEN, 33);
    private final Ball green34 = new Ball(Color.GREEN, 34);
    private final Ball green35 = new Ball(Color.GREEN, 35);
    private final Ball green36 = new Ball(Color.GREEN, 36);
    private final Ball green37 = new Ball(Color.GREEN, 37);
    private final Ball green38 = new Ball(Color.GREEN, 38);
    private final Ball green39 = new Ball(Color.GREEN, 39);
    private final Ball green40 = new Ball(Color.GREEN, 40);
    private final Ball green41 = new Ball(Color.GREEN, 41);
    private final Ball green42 = new Ball(Color.GREEN, 42);
    private final Ball green43 = new Ball(Color.GREEN, 43);
    private final Ball green44 = new Ball(Color.GREEN, 44);
    private final Ball green45 = new Ball(Color.GREEN, 45);
    private final Ball green46 = new Ball(Color.GREEN, 46);
    private final Ball green47 = new Ball(Color.GREEN, 47);
    private final Ball green48 = new Ball(Color.GREEN, 48);
    private final Ball green49 = new Ball(Color.GREEN, 49);
    private final Ball green50 = new Ball(Color.GREEN, 50);
    private final Ball green51 = new Ball(Color.GREEN, 51);
    private final Ball green52 = new Ball(Color.GREEN, 52);
    private final Ball green53 = new Ball(Color.GREEN, 53);
    private final Ball green54 = new Ball(Color.GREEN, 54);
    private final Ball green55 = new Ball(Color.GREEN, 55);
    private final Ball green56 = new Ball(Color.GREEN, 56);
    private final Ball green57 = new Ball(Color.GREEN, 57);
    private final Ball green58 = new Ball(Color.GREEN, 58);
    private final Ball green59 = new Ball(Color.GREEN, 59);
    private final Ball green60 = new Ball(Color.GREEN, 60);
    private final Ball green61 = new Ball(Color.GREEN, 61);
    private final Ball green62 = new Ball(Color.GREEN, 62);
    private final Ball green63 = new Ball(Color.GREEN, 63);
    private final Ball green64 = new Ball(Color.GREEN, 64);
    private final Ball green65 = new Ball(Color.GREEN, 65);
    private final Ball green66 = new Ball(Color.GREEN, 66);
    private final Ball green67 = new Ball(Color.GREEN, 67);
    private final Ball green80 = new Ball(Color.GREEN, 80);
    private final Ball green81 = new Ball(Color.GREEN, 81);
    private final Ball green82 = new Ball(Color.GREEN, 82);
    private final Ball green83 = new Ball(Color.GREEN, 83);

    private final List<Ball> greenBalls = new ArrayList<>(List.of(green0,green1,green2,green3,green4,green5,green6,green7,green8,green9,green10,green11,green12,green13,green14,green15,green16,green17,green18,green19,green20,green21,green22,green23,green24,green25,green26,green27,green28,green29,green30,green31,green32,green33,green34,green35,green36,green37,green38,green39,green40,green41,green42,green43,green44,green45,green46,green47,green48,green49,green50,green51,green52,green53,green54,green55,green56,green57,green58,green59,green60,green61,green62,green63,green64,green65,green66,green67,green80,green81,green82,green83));

    private final Ball red0 = new Ball(Color.RED, 0);
    private final Ball red1 = new Ball(Color.RED, 1);
    private final Ball red2 = new Ball(Color.RED, 2);
    private final Ball red3 = new Ball(Color.RED, 3);
    private final Ball red4 = new Ball(Color.RED, 4);
    private final Ball red5 = new Ball(Color.RED, 5);
    private final Ball red6 = new Ball(Color.RED, 6);
    private final Ball red7 = new Ball(Color.RED, 7);
    private final Ball red8 = new Ball(Color.RED, 8);
    private final Ball red9 = new Ball(Color.RED, 9);
    private final Ball red10 = new Ball(Color.RED, 10);
    private final Ball red11 = new Ball(Color.RED, 11);
    private final Ball red12 = new Ball(Color.RED, 12);
    private final Ball red13 = new Ball(Color.RED, 13);
    private final Ball red14 = new Ball(Color.RED, 14);
    private final Ball red15 = new Ball(Color.RED, 15);
    private final Ball red16 = new Ball(Color.RED, 16);
    private final Ball red17 = new Ball(Color.RED, 17);
    private final Ball red18 = new Ball(Color.RED, 18);
    private final Ball red19 = new Ball(Color.RED, 19);
    private final Ball red20 = new Ball(Color.RED, 20);
    private final Ball red21 = new Ball(Color.RED, 21);
    private final Ball red22 = new Ball(Color.RED, 22);
    private final Ball red23 = new Ball(Color.RED, 23);
    private final Ball red24 = new Ball(Color.RED, 24);
    private final Ball red25 = new Ball(Color.RED, 25);
    private final Ball red26 = new Ball(Color.RED, 26);
    private final Ball red27 = new Ball(Color.RED, 27);
    private final Ball red28 = new Ball(Color.RED, 28);
    private final Ball red29 = new Ball(Color.RED, 29);
    private final Ball red30 = new Ball(Color.RED, 30);
    private final Ball red31 = new Ball(Color.RED, 31);
    private final Ball red32 = new Ball(Color.RED, 32);
    private final Ball red33 = new Ball(Color.RED, 33);
    private final Ball red34 = new Ball(Color.RED, 34);
    private final Ball red35 = new Ball(Color.RED, 35);
    private final Ball red36 = new Ball(Color.RED, 36);
    private final Ball red37 = new Ball(Color.RED, 37);
    private final Ball red38 = new Ball(Color.RED, 38);
    private final Ball red39 = new Ball(Color.RED, 39);
    private final Ball red40 = new Ball(Color.RED, 40);
    private final Ball red41 = new Ball(Color.RED, 41);
    private final Ball red42 = new Ball(Color.RED, 42);
    private final Ball red43 = new Ball(Color.RED, 43);
    private final Ball red44 = new Ball(Color.RED, 44);
    private final Ball red45 = new Ball(Color.RED, 45);
    private final Ball red46 = new Ball(Color.RED, 46);
    private final Ball red47 = new Ball(Color.RED, 47);
    private final Ball red48 = new Ball(Color.RED, 48);
    private final Ball red49 = new Ball(Color.RED, 49);
    private final Ball red50 = new Ball(Color.RED, 50);
    private final Ball red51 = new Ball(Color.RED, 51);
    private final Ball red52 = new Ball(Color.RED, 52);
    private final Ball red53 = new Ball(Color.RED, 53);
    private final Ball red54 = new Ball(Color.RED, 54);
    private final Ball red55 = new Ball(Color.RED, 55);
    private final Ball red56 = new Ball(Color.RED, 56);
    private final Ball red57 = new Ball(Color.RED, 57);
    private final Ball red58 = new Ball(Color.RED, 58);
    private final Ball red59 = new Ball(Color.RED, 59);
    private final Ball red60 = new Ball(Color.RED, 60);
    private final Ball red61 = new Ball(Color.RED, 61);
    private final Ball red62 = new Ball(Color.RED, 62);
    private final Ball red63 = new Ball(Color.RED, 63);
    private final Ball red68 = new Ball(Color.RED, 68);
    private final Ball red69 = new Ball(Color.RED, 69);
    private final Ball red70 = new Ball(Color.RED, 70);
    private final Ball red71 = new Ball(Color.RED, 71);
    private final Ball red84 = new Ball(Color.RED, 84);
    private final Ball red85 = new Ball(Color.RED, 85);
    private final Ball red86 = new Ball(Color.RED, 86);
    private final Ball red87 = new Ball(Color.RED, 87);

    private final List<Ball> redBalls = new ArrayList<>(List.of(red0,red1,red2,red3,red4,red5,red6,red7,red8,red9,red10,red11,red12,red13,red14,red15,red16,red17,red18,red19,red20,red21,red22,red23,red24,red25,red26,red27,red28,red29,red30,red31,red32,red33,red34,red35,red36,red37,red38,red39,red40,red41,red42,red43,red44,red45,red46,red47,red48,red49,red50,red51,red52,red53,red54,red55,red56,red57,red58,red59,red60,red61,red62,red63,red68,red69,red70,red71,red84,red85,red86,red87));

    private final Ball yellow0 = new Ball(Color.YELLOW, 0);
    private final Ball yellow1 = new Ball(Color.YELLOW, 1);
    private final Ball yellow2 = new Ball(Color.YELLOW, 2);
    private final Ball yellow3 = new Ball(Color.YELLOW, 3);
    private final Ball yellow4 = new Ball(Color.YELLOW, 4);
    private final Ball yellow5 = new Ball(Color.YELLOW, 5);
    private final Ball yellow6 = new Ball(Color.YELLOW, 6);
    private final Ball yellow7 = new Ball(Color.YELLOW, 7);
    private final Ball yellow8 = new Ball(Color.YELLOW, 8);
    private final Ball yellow9 = new Ball(Color.YELLOW, 9);
    private final Ball yellow10 = new Ball(Color.YELLOW, 10);
    private final Ball yellow11 = new Ball(Color.YELLOW, 11);
    private final Ball yellow12 = new Ball(Color.YELLOW, 12);
    private final Ball yellow13 = new Ball(Color.YELLOW, 13);
    private final Ball yellow14 = new Ball(Color.YELLOW, 14);
    private final Ball yellow15 = new Ball(Color.YELLOW, 15);
    private final Ball yellow16 = new Ball(Color.YELLOW, 16);
    private final Ball yellow17 = new Ball(Color.YELLOW, 17);
    private final Ball yellow18 = new Ball(Color.YELLOW, 18);
    private final Ball yellow19 = new Ball(Color.YELLOW, 19);
    private final Ball yellow20 = new Ball(Color.YELLOW, 20);
    private final Ball yellow21 = new Ball(Color.YELLOW, 21);
    private final Ball yellow22 = new Ball(Color.YELLOW, 22);
    private final Ball yellow23 = new Ball(Color.YELLOW, 23);
    private final Ball yellow24 = new Ball(Color.YELLOW, 24);
    private final Ball yellow25 = new Ball(Color.YELLOW, 25);
    private final Ball yellow26 = new Ball(Color.YELLOW, 26);
    private final Ball yellow27 = new Ball(Color.YELLOW, 27);
    private final Ball yellow28 = new Ball(Color.YELLOW, 28);
    private final Ball yellow29 = new Ball(Color.YELLOW, 29);
    private final Ball yellow30 = new Ball(Color.YELLOW, 30);
    private final Ball yellow31 = new Ball(Color.YELLOW, 31);
    private final Ball yellow32 = new Ball(Color.YELLOW, 32);
    private final Ball yellow33 = new Ball(Color.YELLOW, 33);
    private final Ball yellow34 = new Ball(Color.YELLOW, 34);
    private final Ball yellow35 = new Ball(Color.YELLOW, 35);
    private final Ball yellow36 = new Ball(Color.YELLOW, 36);
    private final Ball yellow37 = new Ball(Color.YELLOW, 37);
    private final Ball yellow38 = new Ball(Color.YELLOW, 38);
    private final Ball yellow39 = new Ball(Color.YELLOW, 39);
    private final Ball yellow40 = new Ball(Color.YELLOW, 40);
    private final Ball yellow41 = new Ball(Color.YELLOW, 41);
    private final Ball yellow42 = new Ball(Color.YELLOW, 42);
    private final Ball yellow43 = new Ball(Color.YELLOW, 43);
    private final Ball yellow44 = new Ball(Color.YELLOW, 44);
    private final Ball yellow45 = new Ball(Color.YELLOW, 45);
    private final Ball yellow46 = new Ball(Color.YELLOW, 46);
    private final Ball yellow47 = new Ball(Color.YELLOW, 47);
    private final Ball yellow48 = new Ball(Color.YELLOW, 48);
    private final Ball yellow49 = new Ball(Color.YELLOW, 49);
    private final Ball yellow50 = new Ball(Color.YELLOW, 50);
    private final Ball yellow51 = new Ball(Color.YELLOW, 51);
    private final Ball yellow52 = new Ball(Color.YELLOW, 52);
    private final Ball yellow53 = new Ball(Color.YELLOW, 53);
    private final Ball yellow54 = new Ball(Color.YELLOW, 54);
    private final Ball yellow55 = new Ball(Color.YELLOW, 55);
    private final Ball yellow56 = new Ball(Color.YELLOW, 56);
    private final Ball yellow57 = new Ball(Color.YELLOW, 57);
    private final Ball yellow58 = new Ball(Color.YELLOW, 58);
    private final Ball yellow59 = new Ball(Color.YELLOW, 59);
    private final Ball yellow60 = new Ball(Color.YELLOW, 60);
    private final Ball yellow61 = new Ball(Color.YELLOW, 61);
    private final Ball yellow62 = new Ball(Color.YELLOW, 62);
    private final Ball yellow63 = new Ball(Color.YELLOW, 63);
    private final Ball yellow72 = new Ball(Color.YELLOW, 72);
    private final Ball yellow73 = new Ball(Color.YELLOW, 73);
    private final Ball yellow74 = new Ball(Color.YELLOW, 74);
    private final Ball yellow75 = new Ball(Color.YELLOW, 75);
    private final Ball yellow88 = new Ball(Color.YELLOW, 88);
    private final Ball yellow89 = new Ball(Color.YELLOW, 89);
    private final Ball yellow90 = new Ball(Color.YELLOW, 90);
    private final Ball yellow91 = new Ball(Color.YELLOW, 91);

    private final List<Ball> yellowBalls = new ArrayList<>(List.of(yellow0,yellow1,yellow2,yellow3,yellow4,yellow5,yellow6,yellow7,yellow8,yellow9,yellow10,yellow11,yellow12,yellow13,yellow14,yellow15,yellow16,yellow17,yellow18,yellow19,yellow20,yellow21,yellow22,yellow23,yellow24,yellow25,yellow26,yellow27,yellow28,yellow29,yellow30,yellow31,yellow32,yellow33,yellow34,yellow35,yellow36,yellow37,yellow38,yellow39,yellow40,yellow41,yellow42,yellow43,yellow44,yellow45,yellow46,yellow47,yellow48,yellow49,yellow50,yellow51,yellow52,yellow53,yellow54,yellow55,yellow56,yellow57,yellow58,yellow59,yellow60,yellow61,yellow62,yellow63,yellow72,yellow73,yellow74,yellow75,yellow88,yellow89,yellow90,yellow91));

    private final Ball blue0 = new Ball(Color.BLUE, 0);
    private final Ball blue1 = new Ball(Color.BLUE, 1);
    private final Ball blue2 = new Ball(Color.BLUE, 2);
    private final Ball blue3 = new Ball(Color.BLUE, 3);
    private final Ball blue4 = new Ball(Color.BLUE, 4);
    private final Ball blue5 = new Ball(Color.BLUE, 5);
    private final Ball blue6 = new Ball(Color.BLUE, 6);
    private final Ball blue7 = new Ball(Color.BLUE, 7);
    private final Ball blue8 = new Ball(Color.BLUE, 8);
    private final Ball blue9 = new Ball(Color.BLUE, 9);
    private final Ball blue10 = new Ball(Color.BLUE, 10);
    private final Ball blue11 = new Ball(Color.BLUE, 11);
    private final Ball blue12 = new Ball(Color.BLUE, 12);
    private final Ball blue13 = new Ball(Color.BLUE, 13);
    private final Ball blue14 = new Ball(Color.BLUE, 14);
    private final Ball blue15 = new Ball(Color.BLUE, 15);
    private final Ball blue16 = new Ball(Color.BLUE, 16);
    private final Ball blue17 = new Ball(Color.BLUE, 17);
    private final Ball blue18 = new Ball(Color.BLUE, 18);
    private final Ball blue19 = new Ball(Color.BLUE, 19);
    private final Ball blue20 = new Ball(Color.BLUE, 20);
    private final Ball blue21 = new Ball(Color.BLUE, 21);
    private final Ball blue22 = new Ball(Color.BLUE, 22);
    private final Ball blue23 = new Ball(Color.BLUE, 23);
    private final Ball blue24 = new Ball(Color.BLUE, 24);
    private final Ball blue25 = new Ball(Color.BLUE, 25);
    private final Ball blue26 = new Ball(Color.BLUE, 26);
    private final Ball blue27 = new Ball(Color.BLUE, 27);
    private final Ball blue28 = new Ball(Color.BLUE, 28);
    private final Ball blue29 = new Ball(Color.BLUE, 29);
    private final Ball blue30 = new Ball(Color.BLUE, 30);
    private final Ball blue31 = new Ball(Color.BLUE, 31);
    private final Ball blue32 = new Ball(Color.BLUE, 32);
    private final Ball blue33 = new Ball(Color.BLUE, 33);
    private final Ball blue34 = new Ball(Color.BLUE, 34);
    private final Ball blue35 = new Ball(Color.BLUE, 35);
    private final Ball blue36 = new Ball(Color.BLUE, 36);
    private final Ball blue37 = new Ball(Color.BLUE, 37);
    private final Ball blue38 = new Ball(Color.BLUE, 38);
    private final Ball blue39 = new Ball(Color.BLUE, 39);
    private final Ball blue40 = new Ball(Color.BLUE, 40);
    private final Ball blue41 = new Ball(Color.BLUE, 41);
    private final Ball blue42 = new Ball(Color.BLUE, 42);
    private final Ball blue43 = new Ball(Color.BLUE, 43);
    private final Ball blue44 = new Ball(Color.BLUE, 44);
    private final Ball blue45 = new Ball(Color.BLUE, 45);
    private final Ball blue46 = new Ball(Color.BLUE, 46);
    private final Ball blue47 = new Ball(Color.BLUE, 47);
    private final Ball blue48 = new Ball(Color.BLUE, 48);
    private final Ball blue49 = new Ball(Color.BLUE, 49);
    private final Ball blue50 = new Ball(Color.BLUE, 50);
    private final Ball blue51 = new Ball(Color.BLUE, 51);
    private final Ball blue52 = new Ball(Color.BLUE, 52);
    private final Ball blue53 = new Ball(Color.BLUE, 53);
    private final Ball blue54 = new Ball(Color.BLUE, 54);
    private final Ball blue55 = new Ball(Color.BLUE, 55);
    private final Ball blue56 = new Ball(Color.BLUE, 56);
    private final Ball blue57 = new Ball(Color.BLUE, 57);
    private final Ball blue58 = new Ball(Color.BLUE, 58);
    private final Ball blue59 = new Ball(Color.BLUE, 59);
    private final Ball blue60 = new Ball(Color.BLUE, 60);
    private final Ball blue61 = new Ball(Color.BLUE, 61);
    private final Ball blue62 = new Ball(Color.BLUE, 62);
    private final Ball blue63 = new Ball(Color.BLUE, 63);
    private final Ball blue76 = new Ball(Color.BLUE, 76);
    private final Ball blue77 = new Ball(Color.BLUE, 77);
    private final Ball blue78 = new Ball(Color.BLUE, 78);
    private final Ball blue79 = new Ball(Color.BLUE, 79);
    private final Ball blue92 = new Ball(Color.BLUE, 92);
    private final Ball blue93 = new Ball(Color.BLUE, 93);
    private final Ball blue94 = new Ball(Color.BLUE, 94);
    private final Ball blue95 = new Ball(Color.BLUE, 95);

    private final List<Ball> blueBalls = new ArrayList<>(List.of(blue0,blue1,blue2,blue3,blue4,blue5,blue6,blue7,blue8,blue9,blue10,blue11,blue12,blue13,blue14,blue15,blue16,blue17,blue18,blue19,blue20,blue21,blue22,blue23,blue24,blue25,blue26,blue27,blue28,blue29,blue30,blue31,blue32,blue33,blue34,blue35,blue36,blue37,blue38,blue39,blue40,blue41,blue42,blue43,blue44,blue45,blue46,blue47,blue48,blue49,blue50,blue51,blue52,blue53,blue54,blue55,blue56,blue57,blue58,blue59,blue60,blue61,blue62,blue63,blue76,blue77,blue78,blue79,blue92,blue93,blue94,blue95));

    private final List<List<Ball>> allBalls = List.of(greenBalls, redBalls, yellowBalls, blueBalls);

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

        assertEquals(Set.of(1), gameLogicService.checkBallOnTheWayOnStarting(green14, balls, testPossibleMoves));
        assertEquals(Set.of(), gameLogicService.checkBallOnTheWayOnStarting(green63, balls, testPossibleMoves));

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

    // WHEN E.G. 64,65,67 OCCUPIED AND MOVING WITH 65
        Set<Integer> possibleDestinationsGREEN65 = gameLogicService.getPossibleDestinations(Set.of(1), green65, Set.of(green64,green65,green67));
        // TODO: Re-add this
        // assertEquals(Set.of(66), possibleDestinationsGREEN65);
    }

    @Test
    public void checkCanGoOutOfHomeTest() {

        Set<Ball> balls = new HashSet<>(Set.of(green1, green0, green64, green14, green16, green63, green80, red84, yellow88, blue95));

        assertEquals(0, gameLogicService.checkCanGoOutOfHome(green0, balls));
        assertEquals(1, gameLogicService.checkCanGoOutOfHome(green80, balls));
        assertEquals(2, gameLogicService.checkCanGoOutOfHome(red84, balls));
        assertEquals(2, gameLogicService.checkCanGoOutOfHome(yellow88, balls));
        assertEquals(2, gameLogicService.checkCanGoOutOfHome(blue95, balls));
    }

    @Test
    public void getHolesTravelledTest() {

        List<Integer> from80to0 = gameLogicService.getHolesTravelled(0,80,true);
        List<Integer> from84to16 = gameLogicService.getHolesTravelled(16,84,true);
        List<Integer> from88to32 = gameLogicService.getHolesTravelled(32,88,true);
        List<Integer> from92to48 = gameLogicService.getHolesTravelled(48,92,true);

        List<Integer> from64to65 = gameLogicService.getHolesTravelled(65,64,true);
        List<Integer> from68to69 = gameLogicService.getHolesTravelled(69,68,true);
        List<Integer> from72to73 = gameLogicService.getHolesTravelled(73,72,true);
        List<Integer> from76to77 = gameLogicService.getHolesTravelled(77,76,true);

        List<Integer> from0to6 = gameLogicService.getHolesTravelled(6,0,true);
        List<Integer> from62to4 = gameLogicService.getHolesTravelled(4,62,true);
        List<Integer> from4to0 = gameLogicService.getHolesTravelled(0,4,true);
        List<Integer> from0to60 = gameLogicService.getHolesTravelled(60,0,true);
        List<Integer> from1to61 = gameLogicService.getHolesTravelled(61,1,true);
        List<Integer> from2to62 = gameLogicService.getHolesTravelled(62,2,true);
        List<Integer> from3to63 = gameLogicService.getHolesTravelled(63,3,true);
        List<Integer> from63to0 = gameLogicService.getHolesTravelled(0,63,true);
        List<Integer> from14to15 = gameLogicService.getHolesTravelled(15,14,true);
        List<Integer> from14to25 = gameLogicService.getHolesTravelled(25,14,true);

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

        assertEquals(Set.of(1,2,3), gameLogicService.excludeTooLongMoves(green64, possibleMoves));
        assertEquals(Set.of(1,2,3), gameLogicService.excludeTooLongMoves(red68, possibleMoves));
        assertEquals(Set.of(1,2,3), gameLogicService.excludeTooLongMoves(yellow72, possibleMoves));
        assertEquals(Set.of(1,2,3), gameLogicService.excludeTooLongMoves(blue76, possibleMoves));

    }

    @Test
    public void getFreeHomeHolesTest() {

        Set<Ball> balls = new HashSet<Ball>(Set.of(green81,green82,green83,
                red84,red85,red86,
                yellow88,yellow89,yellow91,
                blue92,blue93,blue94));

        Set<Ball> baseFull = new HashSet<Ball>(Set.of(green80,green81,green82,green83));

        assertEquals(Set.of(80), gameLogicService.getFreeHomeHoles(Color.GREEN, balls));
        assertEquals(Set.of(87), gameLogicService.getFreeHomeHoles(Color.RED, balls));
        assertEquals(Set.of(90), gameLogicService.getFreeHomeHoles(Color.YELLOW, balls));
        assertEquals(Set.of(95), gameLogicService.getFreeHomeHoles(Color.BLUE, balls));

        assertEquals(Set.of(), gameLogicService.getFreeHomeHoles(Color.GREEN, baseFull));

    }

    @Test
    public void ballBackToHomeTest() {

        Set<Ball> balls = new HashSet<>(Set.of(green1,green81,green82,green83,
                red17,red84,red85,red86,
                yellow33,yellow88,yellow89,yellow91,
                blue49,blue92,blue93,blue94));

        gameLogicService.ballBackToHome(green1, balls);
        gameLogicService.ballBackToHome(red17, balls);
        gameLogicService.ballBackToHome(yellow33, balls);
        gameLogicService.ballBackToHome(blue49, balls);

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

    @Test
    public void MovesWithOne() {

        Set<Ball> balls = new HashSet<>();

        Set<Integer> possibleMoves = Set.of(1);

        // GREEN
        for (int destination = 1, position = 0; position <= 62; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }

        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,green63,balls));

        for (int destination = 65, position = 64; position <= 66; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }

        // RED
        for (int destination = 1, position = 0; position <= 62; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }

        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,red63,balls));

        assertEquals(Set.of(69),gameLogicService.getPossibleDestinations(possibleMoves,red68,balls));
        assertEquals(Set.of(70),gameLogicService.getPossibleDestinations(possibleMoves,red69,balls));
        assertEquals(Set.of(71),gameLogicService.getPossibleDestinations(possibleMoves,red70,balls));

        // YELLOW
        for (int destination = 1, position = 0; position <= 62; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,yellow63,balls));

        assertEquals(Set.of(73),gameLogicService.getPossibleDestinations(possibleMoves,yellow72,balls));
        assertEquals(Set.of(74),gameLogicService.getPossibleDestinations(possibleMoves,yellow73,balls));
        assertEquals(Set.of(75),gameLogicService.getPossibleDestinations(possibleMoves,yellow74,balls));

        // BLUE
        for (int destination = 1, position = 0; position <= 62; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }

        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,blue63,balls));

        assertEquals(Set.of(77),gameLogicService.getPossibleDestinations(possibleMoves,blue76,balls));
        assertEquals(Set.of(78),gameLogicService.getPossibleDestinations(possibleMoves,blue77,balls));
        assertEquals(Set.of(79),gameLogicService.getPossibleDestinations(possibleMoves,blue78,balls));

    }

    @Test
    public void MovesWithTwo() {

        Set<Ball> balls = new HashSet<>();

        Set<Integer> possibleMoves = Set.of(2);

        // GREEN
        for (int destination = 2, position = 0; position <= 61; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,green62,balls));
        assertEquals(Set.of(1,64),gameLogicService.getPossibleDestinations(possibleMoves,green63,balls));
        assertEquals(Set.of(66),gameLogicService.getPossibleDestinations(possibleMoves,green64,balls));
        assertEquals(Set.of(67),gameLogicService.getPossibleDestinations(possibleMoves,green65,balls));

        // RED
        for (int destination = 2, position = 0; position <= 14; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }
        assertEquals(Set.of(17,68),gameLogicService.getPossibleDestinations(possibleMoves,red15,balls));
        assertEquals(Set.of(70),gameLogicService.getPossibleDestinations(possibleMoves,red68,balls));
        assertEquals(Set.of(71),gameLogicService.getPossibleDestinations(possibleMoves,red69,balls));
        for (int destination = 18, position = 16; position <= 61; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,red62,balls));
        assertEquals(Set.of(1),gameLogicService.getPossibleDestinations(possibleMoves,red63,balls));

        // YELLOW
        for (int destination = 2, position = 0; position <= 30; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }
        assertEquals(Set.of(33,72),gameLogicService.getPossibleDestinations(possibleMoves,yellow31,balls));
        assertEquals(Set.of(74),gameLogicService.getPossibleDestinations(possibleMoves,yellow72,balls));
        assertEquals(Set.of(75),gameLogicService.getPossibleDestinations(possibleMoves,yellow73,balls));
        for (int destination = 34, position = 32; position <= 61; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        // BLUE
        for (int destination = 2, position = 0; position <= 46; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
        assertEquals(Set.of(49,76),gameLogicService.getPossibleDestinations(possibleMoves,blue47,balls));
        assertEquals(Set.of(78),gameLogicService.getPossibleDestinations(possibleMoves,blue76,balls));
        assertEquals(Set.of(79),gameLogicService.getPossibleDestinations(possibleMoves,blue77,balls));
        for (int destination = 50, position = 48; position <= 61; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
    }

    @Test
    public void MovesWithThree() {

        Set<Ball> balls = new HashSet<>();

        Set<Integer> possibleMoves = Set.of(3);

        // GREEN
        for (int destination = 3, position = 0; position <= 60; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,green61,balls));
        assertEquals(Set.of(1,64),gameLogicService.getPossibleDestinations(possibleMoves,green62,balls));
        assertEquals(Set.of(2,65),gameLogicService.getPossibleDestinations(possibleMoves,green63,balls));
        assertEquals(Set.of(67),gameLogicService.getPossibleDestinations(possibleMoves,green64,balls));

        // RED
        for (int destination = 3, position = 0; position <= 13; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }
        assertEquals(Set.of(17,68),gameLogicService.getPossibleDestinations(possibleMoves,red14,balls));
        assertEquals(Set.of(18,69),gameLogicService.getPossibleDestinations(possibleMoves,red15,balls));
        assertEquals(Set.of(71),gameLogicService.getPossibleDestinations(possibleMoves,red68,balls));
        for (int destination = 19, position = 16; position <= 60; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,red61,balls));
        assertEquals(Set.of(1),gameLogicService.getPossibleDestinations(possibleMoves,red62,balls));
        assertEquals(Set.of(2),gameLogicService.getPossibleDestinations(possibleMoves,red63,balls));

        // YELLOW
        for (int destination = 3, position = 0; position <= 29; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }
        assertEquals(Set.of(33,72),gameLogicService.getPossibleDestinations(possibleMoves,yellow30,balls));
        assertEquals(Set.of(34,73),gameLogicService.getPossibleDestinations(possibleMoves,yellow31,balls));
        assertEquals(Set.of(75),gameLogicService.getPossibleDestinations(possibleMoves,yellow72,balls));
        for (int destination = 35, position = 32; position <= 60; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,yellow61,balls));
        assertEquals(Set.of(1),gameLogicService.getPossibleDestinations(possibleMoves,yellow62,balls));
        assertEquals(Set.of(2),gameLogicService.getPossibleDestinations(possibleMoves,yellow63,balls));

        // BLUE
        for (int destination = 3, position = 0; position <= 45; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
        assertEquals(Set.of(49,76),gameLogicService.getPossibleDestinations(possibleMoves,blue46,balls));
        assertEquals(Set.of(50,77),gameLogicService.getPossibleDestinations(possibleMoves,blue47,balls));
        assertEquals(Set.of(79),gameLogicService.getPossibleDestinations(possibleMoves,blue76,balls));
        for (int destination = 51, position = 48; position <= 60; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,blue61,balls));
        assertEquals(Set.of(1),gameLogicService.getPossibleDestinations(possibleMoves,blue62,balls));
        assertEquals(Set.of(2),gameLogicService.getPossibleDestinations(possibleMoves,blue63,balls));
    }

    @Test
    public void MovesWithFour() {

        Set<Ball> balls = new HashSet<>();

        Set<Integer> possibleMoves = Set.of(4, -4);

        // GREEN
        for (int destinationP = 4, destinationN = 60, position = 0; position <= 3; destinationP++, destinationN++, position++) {
            assertEquals(Set.of(destinationP, destinationN), gameLogicService.getPossibleDestinations(possibleMoves, greenBalls.get(position), balls));
        }
        for (int destinationP = 8, destinationN = 0, position = 4; position <= 59; destinationP++, destinationN++, position++) {
            assertEquals(Set.of(destinationP, destinationN), gameLogicService.getPossibleDestinations(possibleMoves, greenBalls.get(position), balls));
        }
        assertEquals(Set.of(0, 56), gameLogicService.getPossibleDestinations(possibleMoves, green60, balls));
        assertEquals(Set.of(1, 64, 57), gameLogicService.getPossibleDestinations(possibleMoves, green61, balls));
        assertEquals(Set.of(2, 65, 58), gameLogicService.getPossibleDestinations(possibleMoves, green62, balls));
        assertEquals(Set.of(3, 66, 59), gameLogicService.getPossibleDestinations(possibleMoves, green63, balls));


        // RED
        for (int destinationP = 4, destinationN = 60, position = 0; position <= 3; destinationP++, destinationN++, position++) {
            assertEquals(Set.of(destinationP, destinationN), gameLogicService.getPossibleDestinations(possibleMoves, redBalls.get(position), balls));
        }
        for (int destinationP = 8, destinationN = 0, position = 4; position <= 12; destinationP++, destinationN++, position++) {
            assertEquals(Set.of(destinationP, destinationN), gameLogicService.getPossibleDestinations(possibleMoves, redBalls.get(position), balls));
        }
        assertEquals(Set.of(17, 68, 9), gameLogicService.getPossibleDestinations(possibleMoves, red13, balls));
        assertEquals(Set.of(18, 69, 10), gameLogicService.getPossibleDestinations(possibleMoves, red14, balls));
        assertEquals(Set.of(19, 70, 11), gameLogicService.getPossibleDestinations(possibleMoves, red15, balls));

        for (int destinationP = 20, destinationN = 12, position = 16; position <= 59; destinationP++, destinationN++, position++) {
            assertEquals(Set.of(destinationP, destinationN), gameLogicService.getPossibleDestinations(possibleMoves, redBalls.get(position), balls));
        }
        assertEquals(Set.of(0, 56), gameLogicService.getPossibleDestinations(possibleMoves, red60, balls));
        assertEquals(Set.of(1, 57), gameLogicService.getPossibleDestinations(possibleMoves, red61, balls));
        assertEquals(Set.of(2, 58), gameLogicService.getPossibleDestinations(possibleMoves, red62, balls));
        assertEquals(Set.of(3, 59), gameLogicService.getPossibleDestinations(possibleMoves, red63, balls));

        // YELLOW
        for (int destinationP = 4, destinationN = 60, position = 0; position <= 3; destinationP++, destinationN++, position++) {
            assertEquals(Set.of(destinationP, destinationN), gameLogicService.getPossibleDestinations(possibleMoves, yellowBalls.get(position), balls));
        }
        for (int destinationP = 8, destinationN = 0, position = 4; position <= 28; destinationP++, destinationN++, position++) {
            assertEquals(Set.of(destinationP, destinationN), gameLogicService.getPossibleDestinations(possibleMoves, yellowBalls.get(position), balls));
        }
        assertEquals(Set.of(33, 72, 25), gameLogicService.getPossibleDestinations(possibleMoves, yellow29, balls));
        assertEquals(Set.of(34, 73, 26), gameLogicService.getPossibleDestinations(possibleMoves, yellow30, balls));
        assertEquals(Set.of(35, 74, 27), gameLogicService.getPossibleDestinations(possibleMoves, yellow31, balls));

        for (int destinationP = 36, destinationN = 28, position = 32; position <= 59; destinationP++, destinationN++, position++) {
            assertEquals(Set.of(destinationP, destinationN), gameLogicService.getPossibleDestinations(possibleMoves, yellowBalls.get(position), balls));
        }
        assertEquals(Set.of(0, 56), gameLogicService.getPossibleDestinations(possibleMoves, yellow60, balls));
        assertEquals(Set.of(1, 57), gameLogicService.getPossibleDestinations(possibleMoves, yellow61, balls));
        assertEquals(Set.of(2, 58), gameLogicService.getPossibleDestinations(possibleMoves, yellow62, balls));
        assertEquals(Set.of(3, 59), gameLogicService.getPossibleDestinations(possibleMoves, yellow63, balls));

        // BLUE
        for (int destinationP = 4, destinationN = 60, position = 0; position <= 3; destinationP++, destinationN++, position++) {
            assertEquals(Set.of(destinationP, destinationN), gameLogicService.getPossibleDestinations(possibleMoves, blueBalls.get(position), balls));
        }
        for (int destinationP = 8, destinationN = 0, position = 4; position <= 44; destinationP++, destinationN++, position++) {
            assertEquals(Set.of(destinationP, destinationN), gameLogicService.getPossibleDestinations(possibleMoves, blueBalls.get(position), balls));
        }
        assertEquals(Set.of(49, 76, 41), gameLogicService.getPossibleDestinations(possibleMoves, blue45, balls));
        assertEquals(Set.of(50, 77, 42), gameLogicService.getPossibleDestinations(possibleMoves, blue46, balls));
        assertEquals(Set.of(51, 78, 43), gameLogicService.getPossibleDestinations(possibleMoves, blue47, balls));

        for (int destinationP = 52, destinationN = 44, position = 48; position <= 59; destinationP++, destinationN++, position++) {
            assertEquals(Set.of(destinationP, destinationN), gameLogicService.getPossibleDestinations(possibleMoves, blueBalls.get(position), balls));
        }
        assertEquals(Set.of(0, 56), gameLogicService.getPossibleDestinations(possibleMoves, blue60, balls));
        assertEquals(Set.of(1, 57), gameLogicService.getPossibleDestinations(possibleMoves, blue61, balls));
        assertEquals(Set.of(2, 58), gameLogicService.getPossibleDestinations(possibleMoves, blue62, balls));
        assertEquals(Set.of(3, 59), gameLogicService.getPossibleDestinations(possibleMoves, blue63, balls));
    }

    @Test
    public void MovesWithFive() {

        Set<Ball> balls = new HashSet<>();

        Set<Integer> possibleMoves = Set.of(5);

        // GREEN
        for (int destination = 5, position = 0; position <= 58; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,green59,balls));
        assertEquals(Set.of(1,64),gameLogicService.getPossibleDestinations(possibleMoves,green60,balls));
        assertEquals(Set.of(2,65),gameLogicService.getPossibleDestinations(possibleMoves,green61,balls));
        assertEquals(Set.of(3,66),gameLogicService.getPossibleDestinations(possibleMoves,green62,balls));
        assertEquals(Set.of(4,67),gameLogicService.getPossibleDestinations(possibleMoves,green63,balls));

        // RED
        for (int destination = 5, position = 0; position <= 11; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }
        assertEquals(Set.of(17,68),gameLogicService.getPossibleDestinations(possibleMoves,red12,balls));
        assertEquals(Set.of(18,69),gameLogicService.getPossibleDestinations(possibleMoves,red13,balls));
        assertEquals(Set.of(19,70),gameLogicService.getPossibleDestinations(possibleMoves,red14,balls));
        assertEquals(Set.of(20,71),gameLogicService.getPossibleDestinations(possibleMoves,red15,balls));

        for (int destination = 21, position = 16; position <= 58; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,red59,balls));

        // YELLOW
        for (int destination = 5, position = 0; position <= 27; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }
        assertEquals(Set.of(33,72),gameLogicService.getPossibleDestinations(possibleMoves,yellow28,balls));
        assertEquals(Set.of(34,73),gameLogicService.getPossibleDestinations(possibleMoves,yellow29,balls));
        assertEquals(Set.of(35,74),gameLogicService.getPossibleDestinations(possibleMoves,yellow30,balls));
        assertEquals(Set.of(36,75),gameLogicService.getPossibleDestinations(possibleMoves,yellow31,balls));

        for (int destination = 37, position = 32; position <= 58; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,yellow59,balls));

        // BLUE
        for (int destination = 5, position = 0; position <= 43; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
        assertEquals(Set.of(49,76),gameLogicService.getPossibleDestinations(possibleMoves,blue44,balls));
        assertEquals(Set.of(50,77),gameLogicService.getPossibleDestinations(possibleMoves,blue45,balls));
        assertEquals(Set.of(51,78),gameLogicService.getPossibleDestinations(possibleMoves,blue46,balls));
        assertEquals(Set.of(52,79),gameLogicService.getPossibleDestinations(possibleMoves,blue47,balls));

        for (int destination = 53, position = 48; position <= 58; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,blue59,balls));
    }

    @Test
    public void MovesWithSix() {

        Set<Ball> balls = new HashSet<>();

        Set<Integer> possibleMoves = Set.of(6);

        // GREEN
        for (int destination = 6, position = 0; position <= 57; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,green58,balls));
        assertEquals(Set.of(1,64),gameLogicService.getPossibleDestinations(possibleMoves,green59,balls));
        assertEquals(Set.of(2,65),gameLogicService.getPossibleDestinations(possibleMoves,green60,balls));
        assertEquals(Set.of(3,66),gameLogicService.getPossibleDestinations(possibleMoves,green61,balls));
        assertEquals(Set.of(4,67),gameLogicService.getPossibleDestinations(possibleMoves,green62,balls));
        assertEquals(Set.of(5),gameLogicService.getPossibleDestinations(possibleMoves,green63,balls));

        // RED
        for (int destination = 6, position = 0; position <= 10; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }
        assertEquals(Set.of(17,68),gameLogicService.getPossibleDestinations(possibleMoves,red11,balls));
        assertEquals(Set.of(18,69),gameLogicService.getPossibleDestinations(possibleMoves,red12,balls));
        assertEquals(Set.of(19,70),gameLogicService.getPossibleDestinations(possibleMoves,red13,balls));
        assertEquals(Set.of(20,71),gameLogicService.getPossibleDestinations(possibleMoves,red14,balls));
        assertEquals(Set.of(21),gameLogicService.getPossibleDestinations(possibleMoves,red15,balls));

        for (int destination = 22, position = 16; position <= 57; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }

        for (int destination = 0, position = 58; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }

        // YELLOW
        for (int destination = 6, position = 0; position <= 26; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }
        assertEquals(Set.of(33,72),gameLogicService.getPossibleDestinations(possibleMoves,yellow27,balls));
        assertEquals(Set.of(34,73),gameLogicService.getPossibleDestinations(possibleMoves,yellow28,balls));
        assertEquals(Set.of(35,74),gameLogicService.getPossibleDestinations(possibleMoves,yellow29,balls));
        assertEquals(Set.of(36,75),gameLogicService.getPossibleDestinations(possibleMoves,yellow30,balls));
        assertEquals(Set.of(37),gameLogicService.getPossibleDestinations(possibleMoves,yellow31,balls));

        for (int destination = 38, position = 32; position <= 57; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        for (int destination = 0, position = 58; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        // BLUE
        for (int destination = 6, position = 0; position <= 42; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
        assertEquals(Set.of(49,76),gameLogicService.getPossibleDestinations(possibleMoves,blue43,balls));
        assertEquals(Set.of(50,77),gameLogicService.getPossibleDestinations(possibleMoves,blue44,balls));
        assertEquals(Set.of(51,78),gameLogicService.getPossibleDestinations(possibleMoves,blue45,balls));
        assertEquals(Set.of(52,79),gameLogicService.getPossibleDestinations(possibleMoves,blue46,balls));
        assertEquals(Set.of(53),gameLogicService.getPossibleDestinations(possibleMoves,blue47,balls));

        for (int destination = 54, position = 48; position <= 57; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }

        for (int destination = 0, position = 58; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
    }

    @Test
    public void MovesWithSeven() {

        Set<Ball> balls = new HashSet<>();

        Set<Integer> possibleMoves = Set.of(7);

        // GREEN
        for (int destination = 7, position = 0; position <= 56; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,green57,balls));
        assertEquals(Set.of(1,64),gameLogicService.getPossibleDestinations(possibleMoves,green58,balls));
        assertEquals(Set.of(2,65),gameLogicService.getPossibleDestinations(possibleMoves,green59,balls));
        assertEquals(Set.of(3,66),gameLogicService.getPossibleDestinations(possibleMoves,green60,balls));
        assertEquals(Set.of(4,67),gameLogicService.getPossibleDestinations(possibleMoves,green61,balls));
        assertEquals(Set.of(5),gameLogicService.getPossibleDestinations(possibleMoves,green62,balls));
        assertEquals(Set.of(6),gameLogicService.getPossibleDestinations(possibleMoves,green63,balls));

        // RED
        for (int destination = 7, position = 0; position <= 9; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }
        assertEquals(Set.of(17,68),gameLogicService.getPossibleDestinations(possibleMoves,red10,balls));
        assertEquals(Set.of(18,69),gameLogicService.getPossibleDestinations(possibleMoves,red11,balls));
        assertEquals(Set.of(19,70),gameLogicService.getPossibleDestinations(possibleMoves,red12,balls));
        assertEquals(Set.of(20,71),gameLogicService.getPossibleDestinations(possibleMoves,red13,balls));
        assertEquals(Set.of(21),gameLogicService.getPossibleDestinations(possibleMoves,red14,balls));

        for (int destination = 22, position = 15; position <= 56; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }

        for (int destination = 0, position = 57; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }

        // YELLOW
        for (int destination = 7, position = 0; position <= 25; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }
        assertEquals(Set.of(33,72),gameLogicService.getPossibleDestinations(possibleMoves,yellow26,balls));
        assertEquals(Set.of(34,73),gameLogicService.getPossibleDestinations(possibleMoves,yellow27,balls));
        assertEquals(Set.of(35,74),gameLogicService.getPossibleDestinations(possibleMoves,yellow28,balls));
        assertEquals(Set.of(36,75),gameLogicService.getPossibleDestinations(possibleMoves,yellow29,balls));
        assertEquals(Set.of(37),gameLogicService.getPossibleDestinations(possibleMoves,yellow30,balls));

        for (int destination = 38, position = 31; position <= 56; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        for (int destination = 0, position = 57; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        // BLUE
        for (int destination = 7, position = 0; position <= 41; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
        assertEquals(Set.of(49,76),gameLogicService.getPossibleDestinations(possibleMoves,blue42,balls));
        assertEquals(Set.of(50,77),gameLogicService.getPossibleDestinations(possibleMoves,blue43,balls));
        assertEquals(Set.of(51,78),gameLogicService.getPossibleDestinations(possibleMoves,blue44,balls));
        assertEquals(Set.of(52,79),gameLogicService.getPossibleDestinations(possibleMoves,blue45,balls));
        assertEquals(Set.of(53),gameLogicService.getPossibleDestinations(possibleMoves,blue46,balls));

        for (int destination = 54, position = 47; position <= 56; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }

        for (int destination = 0, position = 57; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
    }

    @Test
    public void MovesWithEight() {

        Set<Ball> balls = new HashSet<>();

        Set<Integer> possibleMoves = Set.of(8);

        // GREEN
        for (int destination = 8, position = 0; position <= 55; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,green56,balls));
        assertEquals(Set.of(1,64),gameLogicService.getPossibleDestinations(possibleMoves,green57,balls));
        assertEquals(Set.of(2,65),gameLogicService.getPossibleDestinations(possibleMoves,green58,balls));
        assertEquals(Set.of(3,66),gameLogicService.getPossibleDestinations(possibleMoves,green59,balls));
        assertEquals(Set.of(4,67),gameLogicService.getPossibleDestinations(possibleMoves,green60,balls));

        for (int destination = 5, position = 61; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }

        // RED
        for (int destination = 8, position = 0; position <= 8; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }
        assertEquals(Set.of(17,68),gameLogicService.getPossibleDestinations(possibleMoves,red9,balls));
        assertEquals(Set.of(18,69),gameLogicService.getPossibleDestinations(possibleMoves,red10,balls));
        assertEquals(Set.of(19,70),gameLogicService.getPossibleDestinations(possibleMoves,red11,balls));
        assertEquals(Set.of(20,71),gameLogicService.getPossibleDestinations(possibleMoves,red12,balls));
        assertEquals(Set.of(21),gameLogicService.getPossibleDestinations(possibleMoves,red13,balls));

        for (int destination = 22, position = 14; position <= 55; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }

        for (int destination = 0, position = 56; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }

        // YELLOW
        for (int destination = 8, position = 0; position <= 24; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }
        assertEquals(Set.of(33,72),gameLogicService.getPossibleDestinations(possibleMoves,yellow25,balls));
        assertEquals(Set.of(34,73),gameLogicService.getPossibleDestinations(possibleMoves,yellow26,balls));
        assertEquals(Set.of(35,74),gameLogicService.getPossibleDestinations(possibleMoves,yellow27,balls));
        assertEquals(Set.of(36,75),gameLogicService.getPossibleDestinations(possibleMoves,yellow28,balls));
        assertEquals(Set.of(37),gameLogicService.getPossibleDestinations(possibleMoves,yellow29,balls));

        for (int destination = 38, position = 30; position <= 55; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        for (int destination = 0, position = 56; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        // BLUE
        for (int destination = 8, position = 0; position <= 40; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
        assertEquals(Set.of(49,76),gameLogicService.getPossibleDestinations(possibleMoves,blue41,balls));
        assertEquals(Set.of(50,77),gameLogicService.getPossibleDestinations(possibleMoves,blue42,balls));
        assertEquals(Set.of(51,78),gameLogicService.getPossibleDestinations(possibleMoves,blue43,balls));
        assertEquals(Set.of(52,79),gameLogicService.getPossibleDestinations(possibleMoves,blue44,balls));
        assertEquals(Set.of(53),gameLogicService.getPossibleDestinations(possibleMoves,blue45,balls));

        for (int destination = 54, position = 46; position <= 55; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }

        for (int destination = 0, position = 56; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
    }

    @Test
    public void MovesWithNine() {

        Set<Ball> balls = new HashSet<>();

        Set<Integer> possibleMoves = Set.of(9);

        // GREEN
        for (int destination = 9, position = 0; position <= 54; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,green55,balls));
        assertEquals(Set.of(1,64),gameLogicService.getPossibleDestinations(possibleMoves,green56,balls));
        assertEquals(Set.of(2,65),gameLogicService.getPossibleDestinations(possibleMoves,green57,balls));
        assertEquals(Set.of(3,66),gameLogicService.getPossibleDestinations(possibleMoves,green58,balls));
        assertEquals(Set.of(4,67),gameLogicService.getPossibleDestinations(possibleMoves,green59,balls));

        for (int destination = 5, position = 60; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }

        // RED
        for (int destination = 9, position = 0; position <= 7; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }
        assertEquals(Set.of(17,68),gameLogicService.getPossibleDestinations(possibleMoves,red8,balls));
        assertEquals(Set.of(18,69),gameLogicService.getPossibleDestinations(possibleMoves,red9,balls));
        assertEquals(Set.of(19,70),gameLogicService.getPossibleDestinations(possibleMoves,red10,balls));
        assertEquals(Set.of(20,71),gameLogicService.getPossibleDestinations(possibleMoves,red11,balls));
        assertEquals(Set.of(21),gameLogicService.getPossibleDestinations(possibleMoves,red12,balls));

        for (int destination = 22, position = 13; position <= 54; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }

        for (int destination = 0, position = 55; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }

        // YELLOW
        for (int destination = 9, position = 0; position <= 23; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }
        assertEquals(Set.of(33,72),gameLogicService.getPossibleDestinations(possibleMoves,yellow24,balls));
        assertEquals(Set.of(34,73),gameLogicService.getPossibleDestinations(possibleMoves,yellow25,balls));
        assertEquals(Set.of(35,74),gameLogicService.getPossibleDestinations(possibleMoves,yellow26,balls));
        assertEquals(Set.of(36,75),gameLogicService.getPossibleDestinations(possibleMoves,yellow27,balls));
        assertEquals(Set.of(37),gameLogicService.getPossibleDestinations(possibleMoves,yellow28,balls));

        for (int destination = 38, position = 29; position <= 54; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        for (int destination = 0, position = 55; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        // BLUE
        for (int destination = 9, position = 0; position <= 39; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
        assertEquals(Set.of(49,76),gameLogicService.getPossibleDestinations(possibleMoves,blue40,balls));
        assertEquals(Set.of(50,77),gameLogicService.getPossibleDestinations(possibleMoves,blue41,balls));
        assertEquals(Set.of(51,78),gameLogicService.getPossibleDestinations(possibleMoves,blue42,balls));
        assertEquals(Set.of(52,79),gameLogicService.getPossibleDestinations(possibleMoves,blue43,balls));
        assertEquals(Set.of(53),gameLogicService.getPossibleDestinations(possibleMoves,blue44,balls));

        for (int destination = 54, position = 45; position <= 54; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }

        for (int destination = 0, position = 55; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
    }

    @Test
    public void MovesWithTen() {

        Set<Ball> balls = new HashSet<>();

        Set<Integer> possibleMoves = Set.of(10);

        // GREEN
        for (int destination = 10, position = 0; position <= 53; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,green54,balls));
        assertEquals(Set.of(1,64),gameLogicService.getPossibleDestinations(possibleMoves,green55,balls));
        assertEquals(Set.of(2,65),gameLogicService.getPossibleDestinations(possibleMoves,green56,balls));
        assertEquals(Set.of(3,66),gameLogicService.getPossibleDestinations(possibleMoves,green57,balls));
        assertEquals(Set.of(4,67),gameLogicService.getPossibleDestinations(possibleMoves,green58,balls));

        for (int destination = 5, position = 59; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }

        // RED
        for (int destination = 10, position = 0; position <= 6; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }
        assertEquals(Set.of(17,68),gameLogicService.getPossibleDestinations(possibleMoves,red7,balls));
        assertEquals(Set.of(18,69),gameLogicService.getPossibleDestinations(possibleMoves,red8,balls));
        assertEquals(Set.of(19,70),gameLogicService.getPossibleDestinations(possibleMoves,red9,balls));
        assertEquals(Set.of(20,71),gameLogicService.getPossibleDestinations(possibleMoves,red10,balls));
        assertEquals(Set.of(21),gameLogicService.getPossibleDestinations(possibleMoves,red11,balls));

        for (int destination = 22, position = 12; position <= 53; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }

        for (int destination = 0, position = 54; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }

        // YELLOW
        for (int destination = 10, position = 0; position <= 22; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }
        assertEquals(Set.of(33,72),gameLogicService.getPossibleDestinations(possibleMoves,yellow23,balls));
        assertEquals(Set.of(34,73),gameLogicService.getPossibleDestinations(possibleMoves,yellow24,balls));
        assertEquals(Set.of(35,74),gameLogicService.getPossibleDestinations(possibleMoves,yellow25,balls));
        assertEquals(Set.of(36,75),gameLogicService.getPossibleDestinations(possibleMoves,yellow26,balls));
        assertEquals(Set.of(37),gameLogicService.getPossibleDestinations(possibleMoves,yellow27,balls));

        for (int destination = 38, position = 28; position <= 53; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        for (int destination = 0, position = 54; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        // BLUE
        for (int destination = 10, position = 0; position <= 38; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
        assertEquals(Set.of(49,76),gameLogicService.getPossibleDestinations(possibleMoves,blue39,balls));
        assertEquals(Set.of(50,77),gameLogicService.getPossibleDestinations(possibleMoves,blue40,balls));
        assertEquals(Set.of(51,78),gameLogicService.getPossibleDestinations(possibleMoves,blue41,balls));
        assertEquals(Set.of(52,79),gameLogicService.getPossibleDestinations(possibleMoves,blue42,balls));
        assertEquals(Set.of(53),gameLogicService.getPossibleDestinations(possibleMoves,blue43,balls));

        for (int destination = 54, position = 44; position <= 53; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }

        for (int destination = 0, position = 54; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
    }
    @Test
    public void MovesWithEleven() {

        Set<Ball> balls = new HashSet<>();

        Set<Integer> possibleMoves = Set.of(11);

        // GREEN
        for (int destination = 11, position = 0; position <= 52; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,green53,balls));
        assertEquals(Set.of(1,64),gameLogicService.getPossibleDestinations(possibleMoves,green54,balls));
        assertEquals(Set.of(2,65),gameLogicService.getPossibleDestinations(possibleMoves,green55,balls));
        assertEquals(Set.of(3,66),gameLogicService.getPossibleDestinations(possibleMoves,green56,balls));
        assertEquals(Set.of(4,67),gameLogicService.getPossibleDestinations(possibleMoves,green57,balls));

        for (int destination = 5, position = 58; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }

        // RED
        for (int destination = 11, position = 0; position <= 5; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }
        assertEquals(Set.of(17,68),gameLogicService.getPossibleDestinations(possibleMoves,red6,balls));
        assertEquals(Set.of(18,69),gameLogicService.getPossibleDestinations(possibleMoves,red7,balls));
        assertEquals(Set.of(19,70),gameLogicService.getPossibleDestinations(possibleMoves,red8,balls));
        assertEquals(Set.of(20,71),gameLogicService.getPossibleDestinations(possibleMoves,red9,balls));
        assertEquals(Set.of(21),gameLogicService.getPossibleDestinations(possibleMoves,red10,balls));

        for (int destination = 22, position = 11; position <= 52; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }

        for (int destination = 0, position = 53; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }

        // YELLOW
        for (int destination = 11, position = 0; position <= 21; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }
        assertEquals(Set.of(33,72),gameLogicService.getPossibleDestinations(possibleMoves,yellow22,balls));
        assertEquals(Set.of(34,73),gameLogicService.getPossibleDestinations(possibleMoves,yellow23,balls));
        assertEquals(Set.of(35,74),gameLogicService.getPossibleDestinations(possibleMoves,yellow24,balls));
        assertEquals(Set.of(36,75),gameLogicService.getPossibleDestinations(possibleMoves,yellow25,balls));
        assertEquals(Set.of(37),gameLogicService.getPossibleDestinations(possibleMoves,yellow26,balls));

        for (int destination = 38, position = 27; position <= 52; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        for (int destination = 0, position = 53; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        // BLUE
        for (int destination = 11, position = 0; position <= 37; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
        assertEquals(Set.of(49,76),gameLogicService.getPossibleDestinations(possibleMoves,blue38,balls));
        assertEquals(Set.of(50,77),gameLogicService.getPossibleDestinations(possibleMoves,blue39,balls));
        assertEquals(Set.of(51,78),gameLogicService.getPossibleDestinations(possibleMoves,blue40,balls));
        assertEquals(Set.of(52,79),gameLogicService.getPossibleDestinations(possibleMoves,blue41,balls));
        assertEquals(Set.of(53),gameLogicService.getPossibleDestinations(possibleMoves,blue42,balls));

        for (int destination = 54, position = 43; position <= 52; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }

        for (int destination = 0, position = 53; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
    }

    @Test
    public void MovesWithTwelve() {

        Set<Ball> balls = new HashSet<>();

        Set<Integer> possibleMoves = Set.of(12);

        // GREEN
        for (int destination = 12, position = 0; position <= 51; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,green52,balls));
        assertEquals(Set.of(1,64),gameLogicService.getPossibleDestinations(possibleMoves,green53,balls));
        assertEquals(Set.of(2,65),gameLogicService.getPossibleDestinations(possibleMoves,green54,balls));
        assertEquals(Set.of(3,66),gameLogicService.getPossibleDestinations(possibleMoves,green55,balls));
        assertEquals(Set.of(4,67),gameLogicService.getPossibleDestinations(possibleMoves,green56,balls));

        for (int destination = 5, position = 57; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }

        // RED
        for (int destination = 12, position = 0; position <= 4; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }
        assertEquals(Set.of(17,68),gameLogicService.getPossibleDestinations(possibleMoves,red5,balls));
        assertEquals(Set.of(18,69),gameLogicService.getPossibleDestinations(possibleMoves,red6,balls));
        assertEquals(Set.of(19,70),gameLogicService.getPossibleDestinations(possibleMoves,red7,balls));
        assertEquals(Set.of(20,71),gameLogicService.getPossibleDestinations(possibleMoves,red8,balls));
        assertEquals(Set.of(21),gameLogicService.getPossibleDestinations(possibleMoves,red9,balls));

        for (int destination = 22, position = 10; position <= 51; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }

        for (int destination = 0, position = 52; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }

        // YELLOW
        for (int destination = 12, position = 0; position <= 20; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }
        assertEquals(Set.of(33,72),gameLogicService.getPossibleDestinations(possibleMoves,yellow21,balls));
        assertEquals(Set.of(34,73),gameLogicService.getPossibleDestinations(possibleMoves,yellow22,balls));
        assertEquals(Set.of(35,74),gameLogicService.getPossibleDestinations(possibleMoves,yellow23,balls));
        assertEquals(Set.of(36,75),gameLogicService.getPossibleDestinations(possibleMoves,yellow24,balls));
        assertEquals(Set.of(37),gameLogicService.getPossibleDestinations(possibleMoves,yellow25,balls));

        for (int destination = 38, position = 26; position <= 51; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        for (int destination = 0, position = 52; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        // BLUE
        for (int destination = 12, position = 0; position <= 36; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
        assertEquals(Set.of(49,76),gameLogicService.getPossibleDestinations(possibleMoves,blue37,balls));
        assertEquals(Set.of(50,77),gameLogicService.getPossibleDestinations(possibleMoves,blue38,balls));
        assertEquals(Set.of(51,78),gameLogicService.getPossibleDestinations(possibleMoves,blue39,balls));
        assertEquals(Set.of(52,79),gameLogicService.getPossibleDestinations(possibleMoves,blue40,balls));
        assertEquals(Set.of(53),gameLogicService.getPossibleDestinations(possibleMoves,blue41,balls));

        for (int destination = 54, position = 42; position <= 51; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }

        for (int destination = 0, position = 52; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
    }

    @Test
    public void MovesWithThirteen() {

        Set<Ball> balls = new HashSet<>();

        Set<Integer> possibleMoves = Set.of(13);

        // GREEN
        for (int destination = 13, position = 0; position <= 50; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }
        assertEquals(Set.of(0),gameLogicService.getPossibleDestinations(possibleMoves,green51,balls));
        assertEquals(Set.of(1,64),gameLogicService.getPossibleDestinations(possibleMoves,green52,balls));
        assertEquals(Set.of(2,65),gameLogicService.getPossibleDestinations(possibleMoves,green53,balls));
        assertEquals(Set.of(3,66),gameLogicService.getPossibleDestinations(possibleMoves,green54,balls));
        assertEquals(Set.of(4,67),gameLogicService.getPossibleDestinations(possibleMoves,green55,balls));

        for (int destination = 5, position = 56; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,greenBalls.get(position),balls));
        }

        // RED
        for (int destination = 13, position = 0; position <= 3; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }
        assertEquals(Set.of(17,68),gameLogicService.getPossibleDestinations(possibleMoves,red4,balls));
        assertEquals(Set.of(18,69),gameLogicService.getPossibleDestinations(possibleMoves,red5,balls));
        assertEquals(Set.of(19,70),gameLogicService.getPossibleDestinations(possibleMoves,red6,balls));
        assertEquals(Set.of(20,71),gameLogicService.getPossibleDestinations(possibleMoves,red7,balls));
        assertEquals(Set.of(21),gameLogicService.getPossibleDestinations(possibleMoves,red8,balls));

        for (int destination = 22, position = 9; position <= 50; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }

        for (int destination = 0, position = 51; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,redBalls.get(position),balls));
        }

        // YELLOW
        for (int destination = 13, position = 0; position <= 19; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }
        assertEquals(Set.of(33,72),gameLogicService.getPossibleDestinations(possibleMoves,yellow20,balls));
        assertEquals(Set.of(34,73),gameLogicService.getPossibleDestinations(possibleMoves,yellow21,balls));
        assertEquals(Set.of(35,74),gameLogicService.getPossibleDestinations(possibleMoves,yellow22,balls));
        assertEquals(Set.of(36,75),gameLogicService.getPossibleDestinations(possibleMoves,yellow23,balls));
        assertEquals(Set.of(37),gameLogicService.getPossibleDestinations(possibleMoves,yellow24,balls));

        for (int destination = 38, position = 25; position <= 50; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        for (int destination = 0, position = 51; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,yellowBalls.get(position),balls));
        }

        // BLUE
        for (int destination = 13, position = 0; position <= 35; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
        assertEquals(Set.of(49,76),gameLogicService.getPossibleDestinations(possibleMoves,blue36,balls));
        assertEquals(Set.of(50,77),gameLogicService.getPossibleDestinations(possibleMoves,blue37,balls));
        assertEquals(Set.of(51,78),gameLogicService.getPossibleDestinations(possibleMoves,blue38,balls));
        assertEquals(Set.of(52,79),gameLogicService.getPossibleDestinations(possibleMoves,blue39,balls));
        assertEquals(Set.of(53),gameLogicService.getPossibleDestinations(possibleMoves,blue40,balls));

        for (int destination = 54, position = 41; position <= 50; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }

        for (int destination = 0, position = 51; position <= 63; destination++, position++) {
            assertEquals(Set.of(destination),gameLogicService.getPossibleDestinations(possibleMoves,blueBalls.get(position),balls));
        }
    }

    @Test
    public void MovesWithJack() {
        Set<Ball> BaseGoalStartBallsRed = Set.of(red84,red85,red86,red87, red68,red69,red70,red71, red16);
        Set<Ball> BaseGoalStartBallsBlue = Set.of(blue92,blue93,blue94,blue95, blue76,blue77,blue78,blue79, blue48);
        Set<Ball> BaseGoalStartBallsYellow = Set.of(yellow88,yellow89,yellow90,yellow91, yellow72,yellow73,yellow74,yellow75, yellow32);
        Set<Ball> BaseGoalStartBallsGreen = Set.of(green80,green81,green82,green83, green64,green65,green66,green67, green0);
        
        Set<Ball> BaseGoalStartBallsAllColors = new HashSet<>();
        BaseGoalStartBallsAllColors.addAll(BaseGoalStartBallsRed);
        BaseGoalStartBallsAllColors.addAll(BaseGoalStartBallsBlue);
        BaseGoalStartBallsAllColors.addAll(BaseGoalStartBallsYellow);
        BaseGoalStartBallsAllColors.addAll(BaseGoalStartBallsGreen);

    // GREEN
        {
        Color color = Color.GREEN;
        Ball ballToMoveWith = green1;
        Ball ballOnStart = green0;
        Set<Ball> balls = new HashSet<Ball>();

        // Add balls that user can move with
        balls.add(ballToMoveWith);
        // Add balls that user cant move with
        balls.addAll(BaseGoalStartBallsAllColors);
        // Add balls that user can swap with
        balls.addAll(Set.of(red17, yellow33, blue63));

        // All balls that are not his own color and not in Home, Base or Start-position
        Set<Integer> expectedDestinationsWithGreen1 = Set.of(red17.getPosition(), yellow33.getPosition(), blue63.getPosition());

        testMovesWithJack(color, ballToMoveWith, ballOnStart, balls, expectedDestinationsWithGreen1);
        }
    
    // RED
        {
        Color color = Color.RED;
        Ball ballToMoveWith = red12;
        Ball ballOnStart = red16;
        Set<Ball> balls = new HashSet<Ball>();

        // Add balls that user can move with
        balls.add(ballToMoveWith);
        // Add balls that user cant move with
        balls.addAll(BaseGoalStartBallsAllColors);
        // Add balls that user can swap with
        balls.addAll(Set.of(green28, yellow33, blue55));

        // All balls that are not his own color and not in Home, Base or Start-position
        Set<Integer> expectedDestinationsWithGreen1 = Set.of(green28.getPosition(), yellow33.getPosition(), blue55.getPosition());

        testMovesWithJack(color, ballToMoveWith, ballOnStart, balls, expectedDestinationsWithGreen1);
        }

    // YELLOW
        {
        Color color = Color.YELLOW;
        Ball ballToMoveWith = yellow33;
        Ball ballOnStart = yellow32;
        Set<Ball> balls = new HashSet<Ball>();

        // Add balls that user can move with
        balls.add(ballToMoveWith);
        // Add balls that user cant move with
        balls.addAll(BaseGoalStartBallsAllColors);
        // Add balls that user can swap with
        balls.addAll(Set.of(green28, red44, blue55));

        // All balls that are not his own color and not in Home, Base or Start-position
        Set<Integer> expectedDestinationsWithGreen1 = Set.of(green28.getPosition(), red44.getPosition(), blue55.getPosition());

        testMovesWithJack(color, ballToMoveWith, ballOnStart, balls, expectedDestinationsWithGreen1);
        }

    // BLUE
        {
        Color color = Color.BLUE;
        Ball ballToMoveWith = blue49;
        Ball ballOnStart = blue48;
        Set<Ball> balls = new HashSet<Ball>();

        // Add balls that user can move with
        balls.add(ballToMoveWith);
        // Add balls that user cant move with
        balls.addAll(BaseGoalStartBallsAllColors);
        // Add balls that user can swap with
        balls.addAll(Set.of(green28, red33, yellow55));

        // All balls that are not his own color and not in Home, Base or Start-position
        Set<Integer> expectedDestinationsWithGreen1 = Set.of(green28.getPosition(), red33.getPosition(), yellow55.getPosition());

        testMovesWithJack(color, ballToMoveWith, ballOnStart, balls, expectedDestinationsWithGreen1);
        }
    }

    private void testMovesWithJack(Color color, Ball ball, Ball ballOnStart, Set<Ball> balls, Set<Integer> expectedDestinationsWithBall){

        // User can only make move with his own marbles that are not in home or base
        Set<Integer> actualHighlightedBalls = gameLogicService.highlightBalls(null, Rank.JACK, balls, color, color);
        Set<Integer> expectedHighlightedBalls = Set.of(ball.getPosition(), ballOnStart.getPosition());

        assertEquals(expectedHighlightedBalls, actualHighlightedBalls);

        // User can only swap with the expected balls
        Set<Integer> possibleMoves = GameLogicService.getPossibleMoves(null, Rank.JACK, balls, ball);
        Set<Integer> actualDestinationsWithGreen1 = gameLogicService.getPossibleDestinations(possibleMoves, ball, balls);

        assertEquals(expectedDestinationsWithBall, actualDestinationsWithGreen1);
    }

    @Test
    public void myBallsInBaseGetAccessToPartnersBalls() {

        Rank cardRank = Rank.TWO;
        Color playerColor = Color.GREEN;
        Color teammateColor = Color.YELLOW;

        Set<Ball> balls = new HashSet<>(Set.of(green64,green65,green66,green67,
        yellow1,yellow18,yellow72,yellow88));

        assertEquals(Set.of(1,18,72),gameLogicService.highlightBalls(new Game(), cardRank, balls, playerColor, teammateColor));
    }
}
