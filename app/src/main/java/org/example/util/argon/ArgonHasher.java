package org.example.util.argon;

import org.example.util.PasswordHasher;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class ArgonHasher implements PasswordHasher {
  private final Argon2 argon2 = Argon2Factory.create();

  @Override
  public String hash(char[] plainPassword) {
    try {
      return argon2.hash(10, 65536, 1, plainPassword);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      // Wipe confidential data
      argon2.wipeArray(plainPassword);
    }
  }

  @Override
  public boolean verify(char[] plainPassword, String hashedPassword) {
    return argon2.verify(hashedPassword, plainPassword);
  }
}
