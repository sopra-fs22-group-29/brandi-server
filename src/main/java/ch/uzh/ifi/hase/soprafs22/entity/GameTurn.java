package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.uzh.ifi.hase.soprafs22.constant.BallState;
import ch.uzh.ifi.hase.soprafs22.constant.Rank;

public class GameTurn {

    // TODO: MAYBE NEEDS TO BE SEPARATED INTO MORE CLASSES - TOO MUCH CLUTTER

    // When making a move player:
    // 1. Chooses card => possible moves are highlighted
    // 2. Can switch to another card
    // 3. Chooses ball with which to move
    // TODO: 4. Clicks on one of the highlighted positions to go there ???

    private PlayerState activePlayer;
    private Boolean canInvoke;
    private List<Integer> possibleMoves;

    // TODO: THESE WILL COME FROM BOARDSTATE and PLAYERHAND
    private BoardState boardState;
    private List<Ball> balls;
    private PlayerHand playerHand;

    // Normal cards: TWO, THREE, FIVE, SIX, EIGHT, NINE, TEN, QUEEN
    // TODO: THIS COULD BE SEPARATE
    HashMap<String, Integer> normalCards = (HashMap<String, Integer>) Map.of("TWO", 2,
            "THREE", 3,
            "FIVE", 5,
            "SIX", 6,
            "EIGHT", 8,
            "NINE", 9,
            "TEN", 10,
            "QUEEN", 12);

    // Special Cards: FOUR, SEVEN, JACK, KING, ACE, JOKER

    public GameTurn(PlayerState activePlayer) {
        this.activePlayer = activePlayer;
        possibleMoves = new ArrayList<Integer>();

        this.playerHand = activePlayer.getPlayerHand();
        this.balls = boardState.getBalls();
    }

    public void setInvokeTrue() {
        this.canInvoke = true;
    }

    public void setPossibleMoves(List<Integer> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public void playCard(Card card, PlayerState activePlayer, Ball ball) {
        // TODO: will need to remove card from player's hand and
        // make move with makeMove
        // have to remove exact card; need to take care of possible multiplicities in hand
        // will need defined destination
        // int chosenMove = someInt;
        makeMove(ball);
        // activePlayer.getHand().deleteCard(card);
        playerHand.deleteCard(card);

        // TODO: WEBSOCKET CALL TO DISPLAY THE MOVE AND DELETE CARD
    }

    public void makeMove(Ball ball) {
        int startPos = ball.getPosition();
        //ball.setPosition(startPos + chosenMove);
    }

    // Highlight possible moves for a given ball
    public void ballChosen(Card card, Ball ball) {

        calculateMove(card);
        
        //int postPos = ball.setPosition(startPos + move);

        // Check if any ball on the way on starting point
        ArrayList<Integer> startingPoints = (ArrayList<Integer>) List.of(0,16,32,48);

        int startPos = ball.getPosition();

        // for (Ball balll : balls) {
        //     if (startingPoints.contains(balll.getPosition())) {
        //         for (int possibleMove : possibleMoves) {
        //         }
        //     }
        // }

        for (int possibleMove : possibleMoves) {
            for (int i = startPos + 1; i <= startPos + possibleMove; i++) {
                for (Ball balll : balls) {
                    if (startingPoints.contains(balll.getPosition())) {
                        possibleMoves.remove(possibleMove);
                    }
                }
            }
        }

        // TODO: WEBSOCKET CALL TO HIGLIGHT POSSIBLE MOVES AFTER CHOOSING BALL
    }

    // FOR SINGLE CARD
    public void calculateMove(Card card) {

        Rank cardRank = card.getRank();
        if (cardRank != Rank.JOKER) {
            getPossibleMoves(cardRank);
        }
        else {
            Rank jokerRank = chooseJokerRank();
            getPossibleMoves(jokerRank);
        }

        //TODO: WEBSOCKET CALL TO HIGHLIGHT POSSIBLE MOVES WITH CHOSEN CARD
    }

    // FOR THE WHOLE HAND
    // public List<Integer> calculateMoves() {

    //     // get player's cards
    //     List<Card> activeCards = playerHand.getActiveCards();

    //     // for each card in the hand calculate the possible moves
    //     for (Card card : activeCards) {

    //         Rank cardRank = card.getRank();
    //         getPossibleMoves(cardRank);
    //     }

    //     return possibleMoves;
    // }

    public void getPossibleMoves(Rank cardRank) {
        List<Integer> possibleMoves = new ArrayList<Integer>();

        if (normalCards.get(cardRank) != null) {
            possibleMoves.add(normalCards.get(cardRank));
        }
        else if (cardRank.equals(Rank.FOUR)) {
            possibleMoves.add(4);
            possibleMoves.add(-4);
        }
        // TODO: implement cooperation with team member
        // and killing on the way
        else if (cardRank.equals(Rank.SEVEN)) {
            possibleMoves.add(1);
            possibleMoves.add(2);
            possibleMoves.add(3);
            possibleMoves.add(4);
            possibleMoves.add(5);
            possibleMoves.add(6);
            possibleMoves.add(7);
        } 
        else if (cardRank.equals(Rank.JACK)) {
            // EXCHANGE TWO BALLS
            // A marble positioned for the first time at the start, at home or in the
            // target area, may not be exchanged.

            // TODO: COMPLETE IMPLEMENTATION OF THIS WILL REQUIRE BOARDSTATE
            // for (Ball ball : balls) {
            // if (ball.getState() == BallState.BOARD &&
            // ball.getPosition != 0/16/32/48) {
            // possibleMoves.add(ball.getPosition());
            // }
            // }
        } 
        else if (cardRank.equals(Rank.KING)) {
            // MOVE BY 13 OR INVOKE A BALL FROM HOME
            possibleMoves.add(13);
            setInvokeTrue();
        } 
        else if (cardRank.equals(Rank.ACE)) {
            possibleMoves.add(1);
            possibleMoves.add(11);
            setInvokeTrue();
        } 
        // else if (cardRank.equals(Rank.JOKER)) {
        //     // TODO: CAN BE ANY OF ABOVE CARDS
        // }

        // Remove duplicates from possible moves
        Set<Integer> set = new LinkedHashSet<>();
        set.addAll(possibleMoves);
        possibleMoves.clear();
        possibleMoves.addAll(set);

        setPossibleMoves(possibleMoves);
        //return possibleMoves;
    }

    public Rank chooseJokerRank() {
        // TODO: WILL NEED TO SHOW THE USER POSSIBLE CARDS
        // OR JUST HIGHLIGHT THE MOVES ???
        Rank chosenRank = Rank.TWO;
        System.out.println("User chooses joker's rank");
        return chosenRank;
    }

    public void endTurn() {
        // TODO: do we keep track of played turns, their number?
        // do we just create new GameTurn every time a player plays his turn?
        // or do we enable 1 action per turn and then just change the vars of GameTurn?
    }

}
