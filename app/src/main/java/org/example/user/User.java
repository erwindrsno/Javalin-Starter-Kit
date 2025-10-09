package org.example.user;

import java.sql.Timestamp;

public class User {
  private final int id;
  private final String username;
  private final String email;
  private final String password;
  private final int roleId;
  private final Timestamp createdAt;
  private final Timestamp updatedAt;
  private final Timestamp deletedAt;

  private User(Builder builder) {
    this.id = builder.id;
    this.username = builder.username;
    this.email = builder.email;
    this.password = builder.password;
    this.roleId = builder.roleId;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
    this.deletedAt = builder.deletedAt;
  }

  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public int getRoleId() {
    return roleId;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public Timestamp getDeletedAt() {
    return deletedAt;
  }

  public static class Builder {
    private int id;
    private String username;
    private String email;
    private String password;
    private int roleId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public Builder id(int id) {
      this.id = id;
      return this;
    }

    public Builder username(String username) {
      this.username = username;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder roleId(int roleId) {
      this.roleId = roleId;
      return this;
    }

    public Builder createdAt(Timestamp createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder updatedAt(Timestamp updatedAt) {
      this.updatedAt = updatedAt;
      return this;
    }

    public Builder deletedAt(Timestamp deletedAt) {
      this.deletedAt = deletedAt;
      return this;
    }

    public User build() {
      return new User(this);
    }
  }
}
