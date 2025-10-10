package org.example.util;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.example.user.service.*;
import org.example.user.repository.*;

public class PasswordHasherUnitTest {
  private Injector injector;
  private PasswordHasher passwordHasher;

  @BeforeEach
  void setUp() {
    injector = Guice.createInjector(new UtilityModule());
    passwordHasher = injector.getInstance(PasswordHasher.class);
  }

  @Test
  @DisplayName("Hash value should not be null or empty")
  public void testOne() {
    // given
    String password = "T35tOneTw0Thr33";
    char[] charPassword = password.toCharArray();

    // when
    String hashValue = passwordHasher.hash(charPassword);

    // then
    assertThat(hashValue)
        .isNotNull()
        .isNotEmpty();
  }

  @Test
  @DisplayName("Should return true for correct password")
  public void testTwo() {
    // given
    String password = "T35tOneTw0Thr33";
    char[] charPassword = password.toCharArray();
    char[] charPasswordForVerify = password.toCharArray();

    // when
    String hashValue = passwordHasher.hash(charPassword);

    // then
    boolean isValid = passwordHasher.verify(hashValue, charPasswordForVerify);
    assertThat(isValid)
        .isNotNull()
        .isTrue();
  }

  @Test
  @DisplayName("Should return false for incorrect password")
  public void testThree() {
    // given
    String password = "T35tOneTw0Thr33";
    char[] charPassword = password.toCharArray();
    String wrongPassword = "123";
    char[] charWrongPassword = wrongPassword.toCharArray();

    // when
    String hashValue = passwordHasher.hash(charPassword);

    // then
    boolean isValid = passwordHasher.verify(hashValue, charWrongPassword);
    assertThat(isValid)
        .isNotNull()
        .isFalse();
  }
}
