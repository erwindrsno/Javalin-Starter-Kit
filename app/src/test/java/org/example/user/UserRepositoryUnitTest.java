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
    // given - when
    List<User> users = userRepository.getAll();
    // then
    assertThat(users)
        .isNotNull()
        .hasSize(4)
        .extracting(User::getUsername)
        .containsExactlyInAnyOrder("erwin", "gabrielle", "jason", "maria");
  }

  @Test
  @DisplayName("Empty list should not be null")
  public void GET_all_users_with_empty_list() {
    // given
    UserRepositoryMockImpl emptyRepo = new UserRepositoryMockImpl() {
      {
        usersList = List.of();
      }
    };

    // when
    List<User> users = emptyRepo.getAll();

    // then
    assertThat(users)
        .isNotNull()
        .hasSize(0);
  }

  @Test
  @DisplayName("Get user by existed id")
  public void GET_user_by_existed_id() {
    // given
    int existedId = 3;
    // when
    User user = userRepository.getOne(existedId);
    // then
    assertThat(user)
        .isNotNull()
        .extracting(User::getId)
        .isEqualTo(existedId);
  }

  @Test
  @DisplayName("Get user by non existent id")
  public void GET_user_by_non_existent_id() {
    // given
    int nonExistId = 0;
    User user = userRepository.getOne(nonExistId);
    // then
    assertThat(user)
        .isNull();
  }

  @Test
  @DisplayName("Create users and should return the created user")
  public void POST_user() {
    // given
    int initialSize = userRepository.getAll().size();

    User newUser = new User.Builder()
        .id(5)
        .username("newuser")
        .email("newuser@example.com")
        .password("pass123")
        .roleId(2)
        .createdAt(new Timestamp(System.currentTimeMillis()))
        .build();

    // when
    User createdUser = userRepository.create(newUser);

    // then
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
    // given
    int initialSize = userRepository.getAll().size();

    User newUser = new User.Builder()
        .id(5)
        .username("erwin")
        .email("newuser@example.com")
        .password("pass123")
        .roleId(2)
        .createdAt(new Timestamp(System.currentTimeMillis()))
        .build();

    // when
    User createdUser = userRepository.create(newUser);

    // then
    assertThat(createdUser)
        .isNull();
    assertThat(userRepository.getAll())
        .hasSize(initialSize);
  }

  @Test
  @DisplayName("Update a user by id and return the updated user object")
  public void PATCH_user_by_id() {
    // given
    int expectedId = 1;
    String expectedName = "Erwin Darsono";
    String expectedEmail = "erwin@coolmail.com";
    User expectedData = new User.Builder()
        .id(expectedId)
        .username(expectedName)
        .email(expectedEmail)
        .build();

    // when
    User updatedUser = userRepository.update(expectedId, expectedData);

    // then
    assertThat(updatedUser).isNotNull();
    assertThat(updatedUser.getUsername()).isEqualTo(expectedName);
    assertThat(updatedUser.getEmail()).isEqualTo(expectedEmail);
  }

  @Test
  @DisplayName("Update a user by non existent id and return null")
  public void PATCH_user_by_non_existent_id() {
    // given
    int nonExistId = 0;
    String expectedName = "Erwin Darsono";
    String expectedEmail = "erwin@coolmail.com";
    User expectedData = new User.Builder()
        .id(nonExistId)
        .username(expectedName)
        .email(expectedEmail)
        .build();

    // when
    User updatedUser = userRepository.update(nonExistId, expectedData);

    // then
    assertThat(updatedUser).isNull();
  }

  @Test
  @DisplayName("Delete a user by id and return deleted user")
  public void DELETE_user_by_id() {
    // given
    int id = 1;

    // when
    User deletedUser = userRepository.delete(id);

    // then
    assertThat(deletedUser)
        .isNotNull()
        .extracting(User::getId)
        .isEqualTo(id);

    User foundUser = userRepository.getOne(id);
    assertThat(foundUser).isNull();
  }

  @Test
  @DisplayName("Delete a user by non existent id and return null")
  public void DELETE_user_by_non_existent_id() {
    // given
    int id = 0;

    // when
    User deletedUser = userRepository.delete(id);

    // then
    assertThat(deletedUser)
        .isNull();
  }
}
