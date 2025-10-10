package org.example.util;

public interface PasswordHasher {
  String hash(char[] plainPassword);

  boolean verify(String hashedPassword, char[] plainPassword);
}
