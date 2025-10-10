package org.example.user;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.example.user.service.*;
import org.example.user.repository.*;

public class UserServiceUnitTest {
  private Injector injector;
  private UserService userService;

  @BeforeEach
  void setUp() {
    injector = Guice.createInjector(new UserModule());
    userService = injector.getInstance(UserService.class);
  }

  @Test
  @DisplayName("POST_user")
  public void GET_all_users() {
    // given
    User user = new User.Builder()
        .id(5)
        .username("ervin darsono")
        .email("ervindarsono@mail.com")
        .password("123")
        .build();
    // when
    // then
  }

}
