package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.entity.Card;

public class CardExchangeDTO {
    private Card oldCard;
    private Card newCard;

    public Card getOldCard() {
        return this.oldCard;
    }

    public void setOldCard(Card oldCard) {
        this.oldCard = oldCard;
    }

    public Card getNewCard() {
        return this.newCard;
    }

    public void setNewCard(Card newCard) {
        this.newCard = newCard;
    }

}
