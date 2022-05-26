package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.transaction.annotation.Transactional;

import ch.uzh.ifi.hase.soprafs22.constant.Color;
import ch.uzh.ifi.hase.soprafs22.constant.Rank;
import ch.uzh.ifi.hase.soprafs22.entity.websocket.Move;
import ch.uzh.ifi.hase.soprafs22.service.GameLogicService;

@Entity
@Table(name = "GAME", schema = "public")
@Transactional
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String name;
    
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

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "Card_id")
    private Card lastCardPlayed;

    private Integer winnerTeam;

    public Game() {}

    public Game(User player) {
        this.gameOver = false;
        this.gameOn = false;
        this.roundsPlayed = 0;
        this.deck = new Deck();
        this.deck.initialize();
        this.unusedColors = new ArrayList<Color>(Arrays.asList(Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE));
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

        // Used to test gameWinning logic
/*         positionDict.put(Color.GREEN, new ArrayList<>(Arrays.asList(58, 65, 66, 67)));
        positionDict.put(Color.RED, new ArrayList<>(Arrays.asList(10, 69, 70, 71)));
        positionDict.put(Color.YELLOW, new ArrayList<>(Arrays.asList(72, 73, 74, 75)));
        positionDict.put(Color.BLUE, new ArrayList<>(Arrays.asList(42, 77, 78, 79))) */;

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

        // Pop one color from unused colors, fallback color is yellow (seems like a bad thing to do)
        Color userColor = unusedColors.isEmpty() ? Color.YELLOW : unusedColors.remove(0);

        Integer team = getTeamByColor(userColor);
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
            
            this.initPlayerState(player);

            // If game is full, automatically start game
            if(this.isFull()){
                this.startGame();
            }
            return true;
        } else{
            System.out.println("Game has started / is full, Can't add new player with id " + player.getId());
            return false;
        }
    }

    public Boolean isFull(){
        return this.playerStates.size() >= 4;
    }

    public void startGame(){
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

        if(this.gameOver){
            System.out.println("Game is already over, cannot make a move anymore!");
            return false;
        }

        if(!this.isFull()) {
            System.out.println("Game is not full yet, cant make a move");
            return false;
        }

        Ball ball = null;
        for(Ball b: this.boardstate.getBalls()){
            if(b.getId().equals(move.getBallId())){
                ball = b;
                break;
            }
        }
        if(ball == null) return false;

        // Only go to next player if move was not with a SEVEN or all seven holes were travelled with the SEVEN
        if(this.holesTravelled.equals(0) || this.holesTravelled.equals(7)){

            // Remove played card from hand
            PlayerHand hand = this.getNextTurn().getPlayerHand();
            hand.deleteCard(move.getPlayedCard());
            this.nextPlayer();
        } else if(this.holesTravelled < 7) {
            this.lastCardPlayed = move.getPlayedCard();
        } else {
            System.out.println("game.holestravelled  = " + this.holesTravelled + "> 7, this should never happen");
        }

        Ball targetBall = this.boardstate.getBallByPosition(move.getDestinationTile());

        if (targetBall != null) {
            // If ball at destination then move it back to home
            if(move.getPlayedCard().getRank().equals(Rank.JACK)){
                // Swap balls
                GameLogicService.swapBalls(ball, targetBall, this.boardstate.getBalls());
                
                move.setTargetBallNewPosition(targetBall.getPosition());
                move.setTargetBallId(targetBall.getId());

            } else {
                System.out.println("BEFORE: " + targetBall.getPosition());
                System.out.println("BALL MOVED BACK TO HOME");

                GameLogicService.ballBackToHome(targetBall,this.boardstate.getBalls());

                move.setTargetBallNewPosition(targetBall.getPosition());
                move.setTargetBallId(targetBall.getId());

            
                System.out.println("AFTER: " + targetBall.getPosition());
            }
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

        ball.setPosition(move.getDestinationTile());

        checkGameOver();

        return true;        
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

    public Boolean checkGameOver(){
        Boolean teamZeroFinished = true;
        Boolean teamOneFinished = true;
        Set<Ball> balls = this.boardstate.getBalls();
        for(Ball ball: balls){
            if(!ball.checkBallInBase()){
                if(getTeamByColor(ball.getColor()).equals(0)) {
                    teamZeroFinished = false;
                } else{
                    teamOneFinished = false;
                }
            }
        }

        if(teamOneFinished || teamZeroFinished){
            this.gameOver = true;
            this.winnerTeam = teamZeroFinished ? 0 : 1;
            System.out.println("!!! Game is over, team " + winnerTeam + " won!!! ");
        }
        return teamZeroFinished || teamOneFinished;
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

    public String getUuid() {
        return this.uuid;
    }

    public Boolean getGameOver() {
        return this.gameOver;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Boolean getGameOn() {
        return this.gameOn;
    }

    public Integer getRoundsPlayed() {
        return this.roundsPlayed;
    }

    public List<PlayerState> getPlayerStates() {
        return this.playerStates;
    }

    public BoardState getBoardstate() {
        return this.boardstate;
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

    public Integer getWinnerTeam() {
        return this.winnerTeam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public Integer getTeamByColor(Color color){
        Map<Color, Integer> ColorToTeam = Map.of(
            Color.GREEN, 0,
            Color.BLUE, 1,
            Color.RED, 1,
            Color.YELLOW, 0
        );
        return ColorToTeam.get(color);
    }
}
