package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.*;
import javax.servlet.http.PushBuilder;

import ch.uzh.ifi.hase.soprafs22.constant.BallState;
import ch.uzh.ifi.hase.soprafs22.constant.Color;


@Entity
@Table(name = "GAME")
public class Game {

    @Id
    @GeneratedValue
    private Long id;
    
    @Column(nullable = false)
    private Boolean gameOver;

    @Column(nullable = false)
    private Boolean gameOn;
    
    @Column(nullable = false)
    private Integer roundsPlayed;
    
    @OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "PlayerState_id")
    private List<PlayerState> playerStates;

    @OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "BoardState_id")
    private BoardState boardstate;
    
    @OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Deck_id")
    private Deck deck;

    public Game(User player) {    
        this.gameOver = false;
        this.gameOn = false;
        this.roundsPlayed = 0;
        this.deck = new Deck();
        this.playerStates = new ArrayList<PlayerState>();
        this.initPlayerState(player);
        this.initBoardState();
        // this.startNewRound();
    }

    /* Create balls for each player, store in boardstate */
    private void initBoardState(){
        ArrayList<Ball> balls = new ArrayList<>();
        // Add 4 balls for each playerColor
        for(Color color: Color.values()){
            for(int i = 0; i < 4; i++){
                balls.add(new Ball(color, -1, BallState.BASE));
            }
        }
        this.boardstate = new BoardState(balls);
    }

    /* Create PlayerState for every player with 6 cards in playerHand */
    private void initPlayerState(User player){
        PlayerHand playerHand = new PlayerHand();
        ArrayList<Card> cards = new ArrayList<>();

        for(int i = 0; i < 6; i++){
            cards.add(this.deck.drawCard());
        }

        playerHand.drawCards(cards);
        this.playerStates.add(new PlayerState(player, 0, true, playerHand));
    }

    public void addPlayer(User player){
        if(!this.isFull() && !this.gameOn){
            this.initPlayerState(player);
        } else{
            // TODO: Should this throw error?
            System.out.println("Can't add new player");
        }

        if(this.isFull()){
            this.startGame();
        }

    }

    public Boolean isFull(){
        return this.playerStates.size() < 4;
    }

    public void startGame(){
        this.gameOn = true;
    }

    public void startNewRound(){
        this.roundsPlayed += 1;

        List<Integer> amounts = Arrays.asList(6, 5, 4, 3, 2);
        Integer numCardsToPlay = roundsPlayed % 5;

        // Draw new cards for each player
        for(PlayerState playerState : this.playerStates){
            ArrayList<Card> cards = new ArrayList<>();

            for(int i = 0; i < amounts.get(numCardsToPlay); i++){
                cards.add(this.deck.drawCard());
            }

            playerState.drawCards(cards);
        }
    }

    public Boolean checkPlayersOnline(){
        // TODO: Implement checkPlayersOnline
        return false;
    }

    public void pauseGame(){
        this.gameOn = false;
    }

    public void resumeGame(){
        this.gameOn = true;
    }

    public void endGame(){
        this.gameOver = true;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isGameOver() {
        return this.gameOver;
    }

    public Boolean getGameOver() {
        return this.gameOver;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Boolean isGameOn() {
        return this.gameOn;
    }

    public Boolean getGameOn() {
        return this.gameOn;
    }

    public void setGameOn(Boolean gameOn) {
        this.gameOn = gameOn;
    }

    public Integer getRoundsPlayed() {
        return this.roundsPlayed;
    }

    public void setRoundsPlayed(Integer roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

    public List<PlayerState> getPlayerStates() {
        return this.playerStates;
    }

    public void setPlayerStates(ArrayList<PlayerState> playerStates) {
        this.playerStates = playerStates;
    }

    public BoardState getBoardstate() {
        return this.boardstate;
    }

    public void setBoardstate(BoardState boardstate) {
        this.boardstate = boardstate;
    }

    public Deck getDeck() {
        return this.deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

}
