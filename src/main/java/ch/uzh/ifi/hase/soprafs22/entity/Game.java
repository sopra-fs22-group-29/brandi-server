package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Optional;
import java.util.Set;

import javax.persistence.*;

import ch.uzh.ifi.hase.soprafs22.service.GameLogicService;
import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.uzh.ifi.hase.soprafs22.constant.Color;
import ch.uzh.ifi.hase.soprafs22.constant.Rank;
import ch.uzh.ifi.hase.soprafs22.entity.websocket.Move;

import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "GAME")
@Transactional
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String uuid;
    
    @Column(nullable = false)
    private Boolean gameOver;

    @Column(nullable = false)
    private Boolean gameOn;
    
    @Column(nullable = false)
    private Integer roundsPlayed;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "PlayerState_id")
    private List<PlayerState> playerStates;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "BoardState_id")
    private BoardState boardstate;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "Deck_id")
    private Deck deck;

    @ElementCollection(targetClass = Color.class)
    @Column
    private List<Color> unusedColors;

    private Integer activePlayer;

    // Needed for SEVEN
    private Integer holesTravelled;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Card_id")
    private Card lastCardPlayed;

    public Game() {}

    public Game(User player) {
        this.gameOver = false;
        this.gameOn = false;
        this.roundsPlayed = 0;
        this.deck = new Deck();
        this.deck.initialize();
        this.unusedColors = new ArrayList<Color>(Arrays.asList(Color.values()));
        this.playerStates = new ArrayList<PlayerState>();
        this.addPlayer(player);
        this.initBoardState();
        this.uuid = UUID.randomUUID().toString();
        this.activePlayer = 0;
        this.holesTravelled = 0;
        this.lastCardPlayed = null;
    }

    /* Create balls for each player, store in boardstate */
    private void initBoardState(){
        Set<Ball> balls = new HashSet<Ball> ();

        // Information for initializing the balls with correct positions
        Hashtable<Color, List<Integer>> positionDict = new Hashtable<>();
        positionDict.put(Color.GREEN, new ArrayList<>(Arrays.asList(80, 81, 82, 83)));
        positionDict.put(Color.RED, new ArrayList<>(Arrays.asList(84, 85, 86, 87)));
        positionDict.put(Color.YELLOW, new ArrayList<>(Arrays.asList(88, 89, 90, 91)));
        positionDict.put(Color.BLUE, new ArrayList<>(Arrays.asList(92, 93, 94, 95)));

        // Add 4 balls for each playerColor
        for(Color color: Color.values()){
            for(int i = 0; i < 4; i++){
                balls.add(new Ball(color, positionDict.get(color).get(i)));
            }
        }
        this.boardstate = new BoardState(balls);
    }

    /* Create PlayerState for every player with 6 cards in playerHand, assign team and color randomly */
    private void initPlayerState(User player){
        PlayerHand playerHand = new PlayerHand();
        playerHand.drawCards(this.deck.drawCards(6));

        //TODO: Should probably be moved elsewhere
        Map<Color, Integer> ColorToTeam = Map.of(
            Color.GREEN, 0,
            Color.BLUE, 1,
            Color.RED, 1,
            Color.YELLOW, 0
        );
        // Pop one color from unused colors, fallback color is yellow (seems like a bad thing to do)
        Color userColor = unusedColors.isEmpty() ? Color.YELLOW : unusedColors.remove(0);

        Integer team = ColorToTeam.get(userColor);
        this.playerStates.add(new PlayerState(player, team, userColor, true, playerHand));
    }

    /*  
     * If player is not in game yet: Add player, return true
     * If player was already in game: Do nothing, return false
     */
    public Boolean addPlayer(User player){
        if(!this.isFull() && !this.gameOn){
            // Check if user is already in this game, if so dont let user join
            Optional<Game> optGame= player.getGameById(this.id);
            if(!optGame.isEmpty()){
                System.out.println("Player is already in this game, no action required. Returned true");
                return false;
            }

            // Check that user is in no other active game
            // If currentGameId is not present then user is in no game -> proceed normally
            Optional<Long> optGameId = player.getCurrentGameId();
            if (optGameId.isPresent()) {
                if(!optGameId.get().equals(this.id)){
                    // TODO: make sure that a user can't play in multiple game simultaneously
                    //  throw new Error("User is already in a different game");
                } else{
                    // User is already in this game
                    return true;
                }
            }
            
            this.initPlayerState(player);

            // If game is full, automatically start game
            if(this.isFull()){
                this.startGame();
            }
            return true;
        } else{
            // TODO: Should this throw error?
            System.out.println("Game has started / is full, Can't add new player with id " + player.getId());
            return false;
        }
    }

    public Boolean isFull(){
        return this.playerStates.size() >= 4;
    }

    public void startGame(){
        //Check if all Users are in no other active game, should also be checked when adding a player
//        for(PlayerState playerState: this.playerStates){
//            Optional<Long> optGame = playerState.getCurrentGameId();
//            optGame.ifPresent((game) -> {
//                throw new Error("A user is already in an active game, can't start this game");
//            });
//        }
        this.gameOn = true;
    }

    public void startNewRound(){
        this.deck.refillDeck();
        this.roundsPlayed += 1;

        List<Integer> amounts = Arrays.asList(6, 5, 4, 3, 2);
        Integer numCardsToPlay = amounts.get(roundsPlayed % 5);

        // Draw new cards for each player
        for(PlayerState playerState : this.playerStates){
            playerState.drawCards(this.deck.drawCards(numCardsToPlay));
        }
        this.activePlayer = 0;
        this.holesTravelled = 0;
        this.lastCardPlayed = null;
    }

    /**
     * Need to call setHolesTravelled before making move
     * @param move
     * @return Boolean moveExecuted
     */
    public Boolean makeMove(Move move){
        Boolean moveExecuted = false;

        Ball ball = null;
        for(Ball b: this.boardstate.getBalls()){
            if(b.getId().equals(move.getBallId())){
                ball = b;
                break;
            }
        }
        if(ball == null) return false;

        if(this.holesTravelled.equals(0) | this.holesTravelled.equals(7)){
            // Remove played card from hand
            PlayerHand hand = this.getNextTurn().getPlayerHand();
            hand.deleteCard(move.getPlayedCard());
            this.nextPlayer();
        } else if(this.holesTravelled < 7) {
            this.lastCardPlayed = move.getPlayedCard();
        } else {
            System.out.println("game.holestravelled > 7, this should never happen");
        }

        // If ball at destination then move it back to home
        Ball targetBall = this.boardstate.getBallByPosition(move.getDestinationTile());

        if (targetBall != null) {
            System.out.println("BEFORE: " + targetBall.getPosition());
            System.out.println("BALL MOVED BACK TO HOME");

            GameLogicService.ballBackToHome(targetBall,this.boardstate.getBalls());

            move.setTargetBallNewPosition(targetBall.getPosition());
            move.setTargetBallId(targetBall.getId());

            System.out.println("AFTER: " + targetBall.getPosition());
        }

        move.setHolesTravelled(GameLogicService.getHolesTravelled(move.getDestinationTile(), ball.getPosition(), true).stream().mapToInt(i->i).toArray());

        // Move balls travelled over with a seven back to base
        if(move.getPlayedCard().getRank().equals(Rank.SEVEN)){
            for(int position: move.getHolesTravelled()){
                Ball ballToEliminate = boardstate.getBallByPosition(position);
                if(ballToEliminate != null && !ballToEliminate.getId().equals(move.getBallId())){
                    Integer newPosition = GameLogicService.ballBackToHome(ballToEliminate,this.boardstate.getBalls());
                    move.addBallIdsEliminated(ballToEliminate.getId());
                    move.addNewPositions(newPosition);
                    System.out.println("Added ball" + ballToEliminate.getId() + "to list of balls to eliminate");
                }
            }
        }

        //FIXME: Verify that move is a valid move
        ball.setPosition(move.getDestinationTile());
        return moveExecuted;        
    }

    private void nextPlayer(){
        // Reset data used for moving with a 7
        this.holesTravelled = 0;
        this.lastCardPlayed = null;

        // Increase as long as activeplayer has no cards
        for(int i = 0; i < 4; i++){
            this.playerStates.get(this.activePlayer).setIsPlaying(false);
            this.activePlayer = (this.activePlayer + 1) % this.playerStates.size();
            if(!this.getNextTurn().getPlayerHand().isEmpty()){
                this.getNextTurn().setIsPlaying(true);
                return;
            }
        }

        // Means no user can play anymore
        this.activePlayer = null;
    }

    public PlayerState getNextTurn(){
        if(this.activePlayer == null) return null;
        return this.playerStates.get(this.activePlayer);
    }

    /**
     * Swap card with teammate
     * @param user
     * @param card
     * @return New Card for user, null if still waiting for teammate
     */
    public Card exchangeCards(User user, Card card){
        PlayerState userState = getPlayerState(user.getUsername());
        PlayerState teammate = getTeammate(userState);

        Card teamMateCard = teammate.getExchangeCard();

        if(teamMateCard == null){
            userState.setExchangeCard(card);
            System.out.println("Waiting for teammate to give card");
            return null;
        }

        userState.removeCard(card);
        userState.addCard(teamMateCard);

        teammate.removeCard(teamMateCard);
        teammate.addCard(card);

        System.out.println(user.getUsername() + " and " + teammate.getUsername() + " exchanged cards successfully");

        userState.setExchangeCard(null);
        teammate.setExchangeCard(null);
        return teamMateCard;
    }

    // return true if all players have no more cards
    public Boolean allPlayersFinished(){
        for(PlayerState playerstate: this.playerStates){
            if(!playerstate.getPlayerHand().getActiveCards().isEmpty()) return false;
        }
        return true;
    }

    // Check if all players in game are active in this game, only works if game is started already
    public Boolean checkAllPlayersInGame(){
        for(PlayerState playerState: this.playerStates){
            Optional<Long> optGameId = playerState.getCurrentGameId();
            if(optGameId.isPresent()){
                Long gameId = optGameId.get();
                if(gameId.equals(this.id)){
                    continue;
                } else {
                    System.out.println("User is active in a different game");
                    return false;
                }
            } else{
                System.out.println("Couldnt find an active game for user");
            }
        }
        return true;
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

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Integer getHolesTravelled() {
        return this.holesTravelled;
    }

    public void setHolesTravelled(Integer holesTravelled) {
        this.holesTravelled = holesTravelled;
    }

    public PlayerState getPlayerState(String playerName) {
        PlayerState state = null;

        for (PlayerState player : this.playerStates){
            if(player.getPlayer().getUsername().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    public Card getLastCardPlayed() {
        return this.lastCardPlayed;
    }

    public void setLastCardPlayed(Card lastCardPlayed) {
        this.lastCardPlayed = lastCardPlayed;
    }

    @JsonIgnore
    public Optional<Color> getUserColorById(Long id){
        for(PlayerState playerState: this.playerStates){
            if(playerState.getPlayer().getId() == id){
                return Optional.of(playerState.getColor());
            }
        }
        return Optional.empty();
    }

    public void surrenderCards(String username){
        PlayerState playerState = this.getPlayerState(username);
        playerState.getPlayerHand().setActiveCards(new HashSet<>());
        this.nextPlayer();
    }

    @JsonIgnore
    public Color getColorOfTeammate(Color userColor){
        PlayerState user = null;
        for(PlayerState state: this.playerStates){
            if(state.getColor().equals(userColor)){
                user = state;
                break;
            }
        }

        if(user == null) return userColor;
        Integer userTeam = user.getTeam();
        for(PlayerState state: this.playerStates){
            if(state.getTeam().equals(userTeam) && !state.equals(user)){
                return state.getColor();
            }
        }
        return userColor;
    }

    @JsonIgnore
    public PlayerState getTeammate(PlayerState user){
        for(PlayerState state: this.playerStates){
            if(!state.equals(user) && state.getTeam().equals(user.getTeam())){
                return state;
            }
        }    
        return null;
    }

    @JsonIgnore
    public List<PlayerState> getPlayerStatesOfTeam(Integer team){
        List<PlayerState> playerStatesOfTeam = new ArrayList<PlayerState>();
        for(PlayerState state: this.playerStates){
            if(state.getTeam().equals(team)) playerStatesOfTeam.add(state);
        }
        return playerStatesOfTeam;
    }
}
