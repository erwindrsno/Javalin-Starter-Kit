package org.example.user.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;

import com.google.inject.Guice;
import com.google.inject.Injector;

import static org.mockito.Mockito.*;

import org.example.user.repository.*;
import org.example.user.*;
import org.example.util.PasswordHasher;
import org.example.util.UtilityModule;

public class UserServiceUnitTest {
  private Injector injector;
  private UserService userService;
  private PasswordHasher passwordHasher;
  private UserRepository userRepo;

  @BeforeEach
  void setUp() {
    injector = Guice.createInjector(new UtilityModule());
    userRepo = mock(UserRepository.class);
    passwordHasher = injector.getInstance(PasswordHasher.class);
    userService = new UserServiceImpl(userRepo, passwordHasher);
  }

  @Test
  @DisplayName("Should register a new user when username is not already taken")
  public void testOne() {
    // given
    String username = "ervin darsono";
    String email = "ervin@mail.com";
    String password = "ervin123";
    int roleId = 3;
    RegisterRequest registerReq = new RegisterRequest(
        username,
        email,
        password,
        roleId);

    // setup
    when(userRepo.getByUsername(username)).thenReturn(null);
    when(userRepo.create(any(User.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // when
    User registeredUser = userService.register(registerReq);

    // then
    assertThat(registeredUser)
        .isNotNull()
        .extracting(User::getUsername, User::getEmail, User::getRoleId)
        .containsExactly(username, email, roleId);

    assertThat(registeredUser.getPassword())
        .isNotEqualTo(password);

    boolean matches = passwordHasher.verify(
        registeredUser.getPassword(),
        password.toCharArray());
    assertThat(matches).isTrue();
  }

  @Test
  @DisplayName("Should return null when trying to register with a duplicate username")
  public void testTwo() {
    // given
    String username = "erwin";
    String email = "ervin@mail.com";
    String password = "ervin123";
    int roleId = 3;
    RegisterRequest registerReq = new RegisterRequest(
        username,
        email,
        password,
        roleId);

    // setup
    when(userRepo.getByUsername(registerReq.username())).thenReturn(null);

    // when
    User registeredUser = userService.register(registerReq);

    // then
    assertThat(registeredUser)
        .isNull();
  }

  @Test
  @DisplayName("Should successfully log in when email and password are correct")
  public void testThree() {
    // setUp
    User userInDb = new User.Builder()
        .id(10)
        .username("jason brook")
        .email("jasonbrook@mail.com")
        .password(passwordHasher.hash("jasonbrook123".toCharArray()))
        .roleId(2)
        .build();

    // given
    String email = "jasonbrook@mail.com";
    String password = "jasonbrook123";
    LoginRequest loginReq = new LoginRequest(
        email,
        password);

    when(userRepo.getByEmail(loginReq.email())).thenReturn(userInDb);

    // when
    User user = userService.login(loginReq);

    // then
    assertThat(user)
        .isNotNull()
        .extracting(User::getEmail)
        .isEqualTo(email);
  }

  @Test
  @DisplayName("Should return null when login credentials are invalid or not found")
  public void testFour() {
    // given
    String email = "jasonbrook@mail.com";
    String password = "jasonbrook123";
    LoginRequest loginReq = new LoginRequest(
        email,
        password);

    // when
    User user = userService.login(loginReq);

    // then
    assertThat(user)
        .isNull();
  }
}
