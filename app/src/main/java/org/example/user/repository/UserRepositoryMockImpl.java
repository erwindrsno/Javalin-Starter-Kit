package org.example.user.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.example.user.User;

public class UserRepositoryMockImpl implements UserRepository {
  List<User> usersList = new ArrayList<>(List.of(
      new User.Builder()
          .id(1)
          .username("erwin")
          .email("erwin@example.com")
          .password("password123")
          .roleId(1)
          .createdAt(new Timestamp(System.currentTimeMillis()))
          .build(),

      new User.Builder()
          .id(2)
          .username("gabrielle")
          .email("gabrielle@example.com")
          .password("securePass")
          .roleId(2)
          .createdAt(new Timestamp(System.currentTimeMillis()))
          .build(),

      new User.Builder()
          .id(3)
          .username("jason")
          .email("jason@company.com")
          .password("jasonPass")
          .roleId(2)
          .createdAt(new Timestamp(System.currentTimeMillis()))
          .build(),

      new User.Builder()
          .id(4)
          .username("maria")
          .email("maria@domain.com")
          .password("mariaPass")
          .roleId(3)
          .createdAt(new Timestamp(System.currentTimeMillis()))
          .build()));

  @Override
  public User create(User data) {
    boolean isDuplicated = this.usersList.stream()
        .anyMatch(u -> u.getUsername().equalsIgnoreCase(data.getUsername()));
    if (isDuplicated)
      return null;
    return usersList.add(data) ? data : null;
  }

  @Override
  public List<User> getAll() {
    return usersList;
  }

  @Override
  public User getOne(int resourceId) {
    return usersList.stream()
        .filter(user -> user.getId() == resourceId)
        .findFirst()
        .orElse(null);
  }

  @Override
  public User update(int resourceId, User updatedData) {
    for (int i = 0; i < usersList.size(); i++) {
      User existingUser = usersList.get(i);
      if (existingUser.getId() == resourceId) {
        User newUser = new User.Builder()
            .id(existingUser.getId())
            .username(updatedData.getUsername() != null ? updatedData.getUsername() : existingUser.getUsername())
            .email(updatedData.getEmail() != null ? updatedData.getEmail() : existingUser.getEmail())
            .password(updatedData.getPassword() != null ? updatedData.getPassword() : existingUser.getPassword())
            .roleId(updatedData.getRoleId() != 0 ? updatedData.getRoleId() : existingUser.getRoleId())
            .updatedAt(new Timestamp(System.currentTimeMillis()))
            .build();

        this.usersList.set(i, newUser);
        return newUser;
      }
    }
    return null;
  }

  @Override
  public User delete(int resourceId) {
    User deletedUser = null;
    Iterator<User> iterator = usersList.iterator();

    while (iterator.hasNext()) {
      User user = iterator.next();
      if (user.getId() == resourceId) {
        deletedUser = user;
        iterator.remove();
        return deletedUser;
      }
    }
    return null;
  }
}
