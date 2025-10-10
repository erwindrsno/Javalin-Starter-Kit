package org.example.util.argon;

import org.example.util.PasswordHasher;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class ArgonHasher implements PasswordHasher {
  private static final int ITERATIONS = 10;
  private static final int MEMORY = 65536;
  private static final int PARALLELISM = 1;

  private final Argon2 argon2 = Argon2Factory.create();

  @Override
  public String hash(char[] plainPassword) {
    try {
      return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, plainPassword);
    } catch (Exception e) {
      throw new RuntimeException("Failed to hash password", e);
    } finally {
      argon2.wipeArray(plainPassword);
    }
  }

  @Override
  public boolean verify(String hashedPassword, char[] plainPassword) {
    try {
      return argon2.verify(hashedPassword, plainPassword);
    } finally {
      argon2.wipeArray(plainPassword);
    }
  }
}
