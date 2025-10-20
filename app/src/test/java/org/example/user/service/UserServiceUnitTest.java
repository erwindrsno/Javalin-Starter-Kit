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
  @DisplayName("Register user where username is not duplicate")
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
    // User registeredUser = userService.register(username, email, password,
    // roleId);

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
  @DisplayName("Register user where username is duplicate and should return null")
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

    // when
    User registeredUser = userService.register(registerReq);

    // then
    assertThat(registeredUser)
        .isNull();
  }

  @Test
  @DisplayName("Login user, where email and password is given")
  public void testThree() {
    // setUp
    User userInDb = new User.Builder()
        .id(10)
        .username("jason brook")
        .email("jasonbrook@mail.com")
        .password(passwordHasher.hash("jasonbrook123".toCharArray()))
        .roleId(2)
        .build();

    when(userRepo.getByEmail("jasonbrook@mail.com")).thenReturn(userInDb);

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
        .isNotNull()
        .extracting(User::getEmail)
        .isEqualTo(email);
  }

  @Test
  @DisplayName("Login user, when email and password is empty or incorrect, return null")
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
