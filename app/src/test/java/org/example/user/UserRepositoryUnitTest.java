package org.example.user;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.*;

import java.sql.Timestamp;
import java.util.List;

public class UserRepositoryUnitTest {

  private UserRepositoryMockImpl userRepository;

  @BeforeEach
  void setUp() {
    userRepository = new UserRepositoryMockImpl();
  }

  @Test
  @DisplayName("Non-empty list should not be null")
  public void GET_all_users() {
    List<User> users = userRepository.getAll();
    assertThat(users)
        .isNotNull()
        .hasSize(4)
        .extracting(User::getUsername)
        .containsExactlyInAnyOrder("erwin", "gabrielle", "jason", "maria");
  }

  @Test
  @DisplayName("Empty list should not be null")
  public void GET_all_users_with_empty_list() {
    UserRepositoryMockImpl emptyRepo = new UserRepositoryMockImpl() {
      {
        usersList = List.of();
      }
    };
    List<User> users = emptyRepo.getAll();

    assertThat(users)
        .isNotNull()
        .hasSize(0);
  }

  @Test
  @DisplayName("Add users")
  public void POST_user() {
    int initialSize = userRepository.getAll().size();

    User newUser = new User.Builder()
        .id(5)
        .username("newuser")
        .email("newuser@example.com")
        .password("pass123")
        .roleId(2)
        .createdAt(new Timestamp(System.currentTimeMillis()))
        .build();

    User createdUser = userRepository.create(newUser);

    assertThat(createdUser)
        .isNotNull()
        .extracting(User::getUsername)
        .isEqualTo("newuser");

    assertThat(userRepository.getAll())
        .hasSize(initialSize + 1)
        .contains(createdUser);
  }

  @Test
  @DisplayName("Add a user with an existed username and should return null")
  public void POST_user_with_duplicate_name() {
    int initialSize = userRepository.getAll().size();

    User newUser = new User.Builder()
        .id(5)
        .username("erwin")
        .email("newuser@example.com")
        .password("pass123")
        .roleId(2)
        .createdAt(new Timestamp(System.currentTimeMillis()))
        .build();

    User createdUser = userRepository.create(newUser);

    assertThat(createdUser)
        .isNull();

    assertThat(userRepository.getAll())
        .hasSize(initialSize);
  }

  @Test
  @DisplayName("")
}
