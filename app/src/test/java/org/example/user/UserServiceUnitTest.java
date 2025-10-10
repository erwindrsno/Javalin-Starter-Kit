package org.example.user;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.example.user.service.*;

public class UserServiceUnitTest {
  private Injector injector;
  private UserService userService;

  @BeforeEach
  void setUp() {
    injector = Guice.createInjector(new UserModule());
    userService = injector.getInstance(UserService.class);
  }

  @Test
  @DisplayName("Login user, where email and password is given")
  public void testOne() {
    // given
    String email = "erwin@example.com";
    String password = "password123";

    // when
    User user = userService.login(email, password);

    // then
    assertThat(user)
        .isNotNull()
        .extracting(User::getEmail)
        .isEqualTo(email);
  }
}
