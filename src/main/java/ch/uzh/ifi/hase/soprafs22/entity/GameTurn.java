package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.uzh.ifi.hase.soprafs22.constant.BallState;
import ch.uzh.ifi.hase.soprafs22.constant.Rank;

public class GameTurn {

    // TODO: I guess we go with PlayerState instead of player everywhere?

    // TODO: I HAVE IMPLEMENTED HIGHLIGHTING AS FOR THE WHOLE PLAYERHAND
    // I JUST REALISED IT SHOULD RATHER BE FOR A SPECIFIC PICKED CARD
    // TO AVOID CLUTTERED BOARD HIGHLIGHT I GUESS?

    // TODO: NEEDS TO BE SEPARATED INTO MORE CLASSES - TOO MUCH CLUTTER

    private PlayerState activePlayer;
    private PlayerHand playerHand;
    private Boolean canInvoke;

    private List<Integer> possibleMoves = new ArrayList<Integer>();

    // Normal cards: TWO, THREE, FIVE, SIX, EIGHT, NINE, TEN, QUEEN
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
        this.playerHand = activePlayer.getPlayerHand();
    }

    public void setInvokeTrue() {
        canInvoke = true;
    }

    public void playCard(Card card, PlayerState activePlayer) {
        // TODO: will need to remove card from player's hand and
        // make move with makeMove
        // have to remove exact card; need to take care of possible multiplicities in
        // hand
        makeMove(card.getRank());
        playerHand.deleteCard(card);
    }

    // FOR THE WHOLE HAND
    public List<Integer> calculateMoves() {

        // get player's cards
        List<Card> activeCards = playerHand.getActiveCards();

        // for each card in the hand calculate the possible moves
        for (Card card : activeCards) {

            Rank cardRank = card.getRank();

            getPossibleMoves(cardRank);
        }

        return possibleMoves;
    }

    // FOR SINGLE CARD
    public List<Integer> calculateMove(Card card) {

        Rank cardRank = card.getRank();
        getPossibleMoves(cardRank);

        return possibleMoves;
    }

    public void getPossibleMoves(Rank cardRank) {
        if (normalCards.get(cardRank) != null) {
            possibleMoves.add(normalCards.get(cardRank));
        }
        else if (cardRank.equals(Rank.FOUR)) {
            possibleMoves.add(4);
            possibleMoves.add(-4);
        }
        // TODO: implement cooperation with team member
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
            // for (ball : balls) {
            // if (ball.getState() == BallState.BOARD) {
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
        else if (cardRank.equals(Rank.JOKER)) {
            // TODO: CAN BE ANY OF ABOVE CARDS

        }
    }

    public void makeMove(Rank cardRank) {
        // TODO: move with a given card
    }

    public void endTurn() {
        // TODO: do we keep track of played turns, their number?
        // do we just create new GameTurn every time a player plays his turn?
        // or do we enable 1 action per turn and then just change the vars of GameTurn?
    }

}
