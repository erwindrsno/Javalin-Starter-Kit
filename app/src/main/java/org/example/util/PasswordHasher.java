package org.example.util;

public interface PasswordHasher {
  String hash(char[] plainPassword);

  boolean verify(char[] plainPassword, String hashedPassword);
}
