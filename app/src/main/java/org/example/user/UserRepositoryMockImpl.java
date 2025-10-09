package org.example.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
  public User create(User user) {
    boolean isDuplicated = this.usersList.stream()
        .anyMatch(u -> u.getUsername().equalsIgnoreCase(user.getUsername()));
    if (isDuplicated)
      return null;
    return usersList.add(user) ? user : null;
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
  public User update(int resourceId, User user) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public User delete(int resourceId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }
}
