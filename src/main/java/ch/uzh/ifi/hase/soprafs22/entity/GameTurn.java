package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Rank;

public class GameTurn {

    //TODO: I guess we go with PlayerState instead of player everywhere?

    private PlayerState activePlayer;
    private PlayerHand playerHand;

    public GameTurn(PlayerState activePlayer) {
        this.activePlayer = activePlayer;
        this.playerHand = activePlayer.getPlayerHand();
    }

    public void playCard(Card card, PlayerState activePlayer) {
        // TODO: will need to remove card from player's hand and
        // make move with makeMove
        // have to remove exact card; need to take care of possible multiplicities in hand
        makeMove(card.getRank());
        playerHand.deleteCard(card);
    }

    public int calculateMoves() {
        // TODO: return possible moves combination
        // not sure about return type here
        

        int someInt = 0;
        return someInt;
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
