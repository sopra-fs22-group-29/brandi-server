package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  private User testUser;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    // given
    testUser = new User();
    testUser.setId(1L);
    testUser.setPassword("testName");
    testUser.setUsername("testUsername");

    // when -> any object is being save in the userRepository -> return the dummy
    // testUser
    Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUser);
  }

  @Test
  public void createUser_validInputs_success() {
    // when -> any object is being save in the userRepository -> return the dummy
    // testUser
    User createdUser = userService.createUser(testUser);

    // then
    Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

    assertEquals(testUser.getId(), createdUser.getId());
    assertEquals(testUser.getUsername(), createdUser.getUsername());
    assertNotNull(createdUser.getToken());
    assertEquals(UserStatus.OFFLINE, createdUser.getStatus());
  }

  @Test
  public void createUser_duplicateName_throwsException() {
    // given -> a first user has already been created
    userService.createUser(testUser);

    // when -> setup additional mocks for UserRepository
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

    // then -> attempt to create second user with same user -> check that an error
    // is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
  }

  @Test
  public void createUser_duplicateInputs_throwsException() {
    // given -> a first user has already been created
    userService.createUser(testUser);

    // when -> setup additional mocks for UserRepository
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

    // then -> attempt to create second user with same user -> check that an error
    // is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
  }

  @Test
  public void createUser_getUser_adequateUser() {
      // given a user with id 1
      userService.createUser(testUser);

      // when -> setup additional mocks for UserRepository
      Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(testUser));

      // get user by id from UserRepository
      User user = userService.getUser(1L);

      // check whether proper user fetched
      assertEquals(testUser, user);
  }

    @Test
    public void createUser_getUserWithWrongId_throwsException() {
        // given a user with id 1
        userService.createUser(testUser);

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(testUser));

        // search for a user with wrong id
        assertThrows(ResponseStatusException.class, () -> {userService.getUser(2L);});
    }

    @Test
    public void createUser_updateUserWithUsernameAndPassword_updatedUser() {
        // given a user with id 1
        userService.createUser(testUser);

        // user input
        User userInput = new User("userInputUsername", "userInputPassword");

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(testUser));

        // update the testUser with username and password
        User updatedUser = userService.updateUser(1L, userInput, "testUsername");

        // check whether testUser was updated
        assertEquals(testUser.getUsername(), updatedUser.getUsername());
        assertEquals(testUser.getPassword(), updatedUser.getPassword());

    }

    @Test
    public void createUser_updateUserWithUsernameOnly_updatedUser() {
        // given a user with id 1
        userService.createUser(testUser);

        // user input
        User userInput = new User("userInputUsername", "");

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(testUser));

        // update the testUser with username and password
        User updatedUser = userService.updateUser(1L, userInput, "testUsername");

        // check whether testUser username was updated
        assertEquals(testUser.getUsername(), updatedUser.getUsername());

    }

    @Test
    public void createUser_updateUser_userNameNotMatched_throwsException() {
        // given a user with id 1
        userService.createUser(testUser);

        // user input
        User userInput = new User("userInputUsername", "userInputPassword");

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(testUser));

        // try to updateUser with wrong username
        assertThrows(ResponseStatusException.class, () -> {userService.updateUser(1L, userInput, "wrongUsername");});

    }
}
