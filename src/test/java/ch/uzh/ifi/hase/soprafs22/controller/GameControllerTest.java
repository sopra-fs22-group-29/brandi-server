package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.Color;
import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.IdDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.AuthenticatedUserService;
import ch.uzh.ifi.hase.soprafs22.service.GameLogicService;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.InGameWebsocketService;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * https://stevenschwenke.de/DontComponentScanInMainSpringApplicationClassWhenUsingSpringWebMVCTest
     * Had a problem like this when adding GameLogicService to the GameController class:
     * NoSuchBeanDefinitionException: No qualifying bean of type 'ch.uzh.ifi.hase.soprafs22.service.GameLogicService' available:
     *   expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {} 
     * Has something to do with the test automatically loading different beans which use UserRepository which then leads to errors (... I think)
     * Mocking all those beans solved the problem
     */
    @MockBean
    private GameService gameService;
    @MockBean
    private UserService userService;
    @MockBean
    private GameLogicService gameLogicService;
    @MockBean
    private AuthenticatedUserService authenticatedUserService;
    @MockBean
    private InGameWebsocketService inGameWebsocketService;

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void createGame_validInput_gameCreated() throws Exception {

        // given
        String gameUUID = "123e4567-e89b-12d3-a456-426614174000";

        IdDTO idDTO = new IdDTO();
        idDTO.setId(1L);
        idDTO.setName("test");

        given(gameService.createGame(Mockito.any(), Mockito.any())).willReturn(gameUUID);

        MockHttpServletRequestBuilder postRequest = post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(idDTO));

        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", is(gameUUID)));

    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void givenGames_whenGetGames_thenReturnJsonArray() throws Exception {

        // given
        List<Game> allGames = new ArrayList<>();
        given(gameService.getGames()).willReturn(allGames);

        // when
        MockHttpServletRequestBuilder getRequest = get("/game").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void givenUuid_whenGetGameByUuid_thenReturnJsonArray() throws Exception {

        // given
        User testPlayer = new User("testUsername", "testPassword");

        Game game = new Game(testPlayer);
        game.setName("test");

        game.addPlayer(testPlayer);

        given(gameService.getGameByUuidOfUser(Mockito.any(), Mockito.any())).willReturn(game);

        // when
        MockHttpServletRequestBuilder getRequest = get("/game/\"123e4567-e89b-12d3-a456-426614174000\"").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("test")));

    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    void joinGame() throws Exception {


        User testPlayer = new User("testUsername", "testPassword");

        Game game = new Game(testPlayer);
        game.setName("test");

        game.addPlayer(testPlayer);

        given(gameService.joinGame(Mockito.any(), Mockito.any())).willReturn(true);

        // when
        MockHttpServletRequestBuilder getRequest = put("/game/\"123e4567-e89b-12d3-a456-426614174000\"").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }
}
