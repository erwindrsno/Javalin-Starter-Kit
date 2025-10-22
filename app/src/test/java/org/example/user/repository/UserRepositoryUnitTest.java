package org.example.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.List;

import org.example.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserRepositoryUnitTest {

  private UserRepositoryMockImpl userRepository;

  @BeforeEach
  void setUp() {
    userRepository = new UserRepositoryMockImpl();
  }

  @Test
  @DisplayName("Non-empty list should not be null")
  public void testOne() {
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
  public void testTwo() {
    // given
    UserRepositoryMockImpl emptyRepo = new UserRepositoryMockImpl() {
      {
        this.usersList = List.of();
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
  public void testThree() {
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
  public void testFour() {
    // given
    int nonExistId = 0;
    User user = userRepository.getOne(nonExistId);
    // then
    assertThat(user)
        .isNull();
  }

  @Test
  @DisplayName("Create users and should return the created user")
  public void testFive() {
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
  @DisplayName("Update a user by id and return the updated user object")
  public void testSeven() {
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
  public void testEight() {
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
  public void testNine() {
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
  public void testTen() {
    // given
    int id = 0;

    // when
    User deletedUser = userRepository.delete(id);

    // then
    assertThat(deletedUser)
        .isNull();
  }

  @Test
  @DisplayName("Get user by email")
  public void testEleven() {
    // given
    String email = "erwin@example.com";

    // when
    User user = userRepository.getByEmail(email);

    // then
    assertThat(user)
        .isNotNull()
        .extracting(User::getEmail)
        .isEqualTo(email);
  }

  @Test
  @DisplayName("Given user email, if not valid return null")
  public void testTwelve() {
    // given
    String email = "rickroll@email.com";

    // when
    User user = userRepository.getByEmail(email);

    // then
    assertThat(user).isNull();
  }

  @Test
  @DisplayName("Get user by username")
  public void testThirteen() {
    // given
    String username = "erwin";

    // when
    User user = userRepository.getByUsername(username);

    // then
    assertThat(user)
        .isNotNull()
        .extracting(User::getUsername)
        .isEqualTo(username);
  }

  @Test
  @DisplayName("Given user username, if not valid return null")
  public void testFourteen() {
    // given
    String username = "rickroll@email.com";

    // when
    User user = userRepository.getByUsername(username);

    // then
    assertThat(user).isNull();
  }
}
