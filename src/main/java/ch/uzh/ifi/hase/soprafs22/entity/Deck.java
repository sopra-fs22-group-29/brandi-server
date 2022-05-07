package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;

import org.springframework.web.client.RestTemplate;
import ch.uzh.ifi.hase.soprafs22.constant.Rank;
import ch.uzh.ifi.hase.soprafs22.constant.Suit;
import ch.uzh.ifi.hase.soprafs22.rest.dto.DeckOfCardsAPI.*;

@Entity
public class Deck {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String deck_id;

    @Transient
    private RestTemplate restTemplate;

    @Transient
    private String baseUrl;

    public Deck(){
        this.baseUrl = "https://deckofcardsapi.com/api/";
        String url = this.baseUrl + "deck/new/shuffle/?deck_count=2&jokers_enabled=true";

        this.restTemplate = new RestTemplate();
        DeckDTO result = this.restTemplate.getForObject(url, DeckDTO.class);

        this.deck_id = result.getDeck_id();
    }

    public void refillDeck(){
        String url = String.format("%sdeck/%s/return/", this.baseUrl, this.deck_id);
        this.restTemplate.getForObject(url, String.class);
        System.out.println("Deck was refilled successfully");
    }

    public Card drawCard(){
        String url = String.format("%sdeck/%s/draw/?count=1", this.baseUrl, this.deck_id);
        DrawCardsDTO result =  this.restTemplate.getForObject(url, DrawCardsDTO.class);
        return this.toCard(result.getCards().get(0));
    }

    public Set<Card> drawCards(int amount){
        Set<Card> cards = new HashSet<>();
        
        String url = String.format("%sdeck/%s/draw/?count=%d", this.baseUrl, this.deck_id, amount);
        DrawCardsDTO result =  this.restTemplate.getForObject(url, DrawCardsDTO.class);
        if(!result.getSuccess()){
            this.refillDeck();
            result =  this.restTemplate.getForObject(url, DrawCardsDTO.class);
        }
        for(CardDTO cardDTO: result.getCards()){
            cards.add(this.toCard(cardDTO));
        }
        
        return cards;
    }

    /**
     * 
     * @param cardDTO Card in representation from DeckOfCardsAPI
     * @return Card in internal representation 
     */
    private Card toCard(CardDTO cardDTO){
        // Joker has suit black, jokers suit doesnt matter anyway so map to club as default
        Map<String, Suit> suitMap = Map.of(
            "DIAMONDS", Suit.DIAMOND,
            "CLUBS", Suit.CLUB,
            "SPADES", Suit.SPADE,
            "HEARTS", Suit.HEART,
            "BLACK",  Suit.CLUB
        );

        // Map.of only allows 10 KV pairs, use Map.ofEntries
        Map<String, Rank> rankMap = Map.ofEntries(
            Map.entry("2", Rank.TWO),
            Map.entry("3", Rank.THREE),
            Map.entry("4", Rank.FOUR),
            Map.entry("5", Rank.FIVE),
            Map.entry("6", Rank.SIX),
            Map.entry("7", Rank.SEVEN),
            Map.entry("8", Rank.EIGHT),
            Map.entry("9", Rank.NINE),
            Map.entry("10", Rank.TEN),
            Map.entry("JACK", Rank.JACK),
            Map.entry("QUEEN", Rank.QUEEN),
            Map.entry("KING", Rank.KING),
            Map.entry("ACE", Rank.ACE),
            Map.entry("JOKER", Rank.JOKER)
        );

        Rank rank = rankMap.get(cardDTO.getValue());
        Suit suit = suitMap.get(cardDTO.getSuit());
        return new Card(rank, suit);
    }
}
