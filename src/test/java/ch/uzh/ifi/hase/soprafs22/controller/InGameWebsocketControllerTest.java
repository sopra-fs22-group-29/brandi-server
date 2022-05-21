package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.Rank;
import ch.uzh.ifi.hase.soprafs22.constant.Suit;
import ch.uzh.ifi.hase.soprafs22.entity.*;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.CardDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SelectMarbleDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.websocket.MovePostDTO;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InGameWebsocketControllerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private GameService gameService;
    @Autowired
    private GameRepository gameRepository;

    @LocalServerPort
    private Integer port;

    private static WebSocketStompClient webSocketStompClient;
    private static StompSession stompSession;
    BlockingQueue<Object> blockingQueue = new ArrayBlockingQueue<>(1);
    private static String sessionId;

    // player_1 being the lobby leader (creator of the lobby)
    private static User player_1;
    private static User player_2;
    private static User player_3;
    private static User player_4;
    private static Long marbleId;
    private static Card kingCard;

    private static String gameUuid;

    private static boolean setupDatabaseDone = false;

    @BeforeEach
    void setUp() throws ExecutionException, InterruptedException, TimeoutException {

        if(!setupDatabaseDone) {
            // first, create four Users
            initializeUsers();

            // create a new game and join with all users
            initializeGame();

            setupDatabaseDone = true;
        }

        webSocketStompClient = new WebSocketStompClient(new SockJsClient(
                List.of(new WebSocketTransport(new StandardWebSocketClient()))
        ));

        String auth = player_1.getUsername() + ":" + "test";
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        System.out.println(auth);
        System.out.println(authHeader);

        webSocketStompClient.setMessageConverter(new SimpleMessageConverter());
        stompSession = webSocketStompClient
                .connect(String.format("ws://localhost:%d/websocket", port),
                        new WebSocketHttpHeaders(new HttpHeaders() {{
                            set("Authorization", authHeader);
                        }}),
                        new StompHeaders() {{
                            set("Authorization", authHeader);
                        }},
                        new StompSessionHandlerAdapter() {
                        })
                .get(1, TimeUnit.SECONDS);

        sessionId = stompSession.getSessionId();
        System.out.println(webSocketStompClient);
        System.out.println("session id: " + sessionId);

        // subscriptions aren't working yet because of the -user{sessionId}
        stompSession.subscribe("/client/state" + "-user" + sessionId, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return PlayerState.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                blockingQueue.add((PlayerState) payload);
            }
        });
    }

    @Test
    void joinGameByUuid() {
        stompSession.send("/app/websocket/" + gameUuid + "/join", null);
        // we can check for changes serverside, since we don't have access to the response
//        System.out.println("will poll");
//        assertEquals("Hello, Mike!", blockingQueue.poll(1, TimeUnit.SECONDS));
//        System.out.println("did poll");
    }

    @Test
    void selectCard() {
        String cardString = "{ \"index\": 0, \"rank\": \"KING\", \"suit\": \"CLUB\" }";
        webSocketStompClient.setMessageConverter(new SimpleMessageConverter());
        stompSession.send("/app/websocket/" + gameUuid + "/select/card", cardString.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void selectMarble() {
        String marbleString = "{ \"cardIndex\": 0, \"rank\": \"KING\", \"suit\": \"CLUB\", \"marbleId\": " + marbleId + " }";
        webSocketStompClient.setMessageConverter(new SimpleMessageConverter());
        stompSession.send("/app/websocket/" + gameUuid + "/select/marble", marbleString.getBytes(StandardCharsets.UTF_8));

    }

    @Test
    void move() {
        String moveString = "{ \"playerCard\": { \"id\": " + kingCard.getId() +", \"ballId\": " + marbleId + ", \"destinationTile\": 0 }, \"marbleId\": " + marbleId + " }";
        webSocketStompClient.setMessageConverter(new SimpleMessageConverter());
        stompSession.send("/app/websocket/" + gameUuid + "/move", moveString.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void leaveGameByUuid() {
        stompSession.send("/app/websocket/" + gameUuid + "/leave", null);
    }

    @Test
    void exchangeCards() {
        webSocketStompClient.setMessageConverter(new SimpleMessageConverter());
        stompSession.send("/app/websocket/" + gameUuid + "/exchangeCard", kingCard.toString().getBytes(StandardCharsets.UTF_8));
    }


    private void initializeUsers() {
        System.out.println("initialize users");
        User p1 = new User();
        User p2 = new User();
        User p3 = new User();
        User p4 = new User();

        p1.setUsername("player1");
        p1.setPassword("test");
        p2.setUsername("player2");
        p2.setPassword("test");
        p3.setUsername("player3");
        p3.setPassword("test");
        p4.setUsername("player4");
        p4.setPassword("test");

        player_1 = userService.createUser(p1);
        player_2 = userService.createUser(p2);
        player_3 = userService.createUser(p3);
        player_4 = userService.createUser(p4);
        System.out.println("initialized users");
    }

    private void initializeGame() {
        System.out.println("initialize game");
        gameUuid = gameService.createGame(player_1.getId());
        Game game = gameService.getGameByUuid(gameUuid);

        PlayerState player_1PlayerState = game.getPlayerState(player_1.getUsername());
        player_1PlayerState.getPlayerHand().setActiveCards(new HashSet<>() {
            {
                add(new Card(Rank.KING, Suit.CLUB));
                add(new Card(Rank.TWO, Suit.CLUB));
            }
        });
        gameRepository.saveAndFlush(game);
        for(Card card : player_1PlayerState.getPlayerHand().getActiveCards()){
            if(card.getRank() == Rank.KING) {
                 kingCard = card;
            } else if(card.getRank() == Rank.TWO) {
            }
        }
        for(Ball ball : game.getBoardstate().getBalls()){
            if(ball.getColor() == player_1PlayerState.getColor()) {
                marbleId = ball.getId();
                break;
            }
        }

        gameService.joinGame(gameUuid, player_2.getUsername());
        gameService.joinGame(gameUuid, player_3.getUsername());
        gameService.joinGame(gameUuid, player_4.getUsername());
        System.out.println("initialized game");
    }

}
