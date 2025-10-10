package org.example.user.service;

import java.util.List;

import com.google.inject.Inject;

import org.example.user.repository.UserRepository;
import org.example.user.User;
import org.example.util.PasswordHasher;

public class UserServiceImpl implements UserService {
  private final UserRepository userRepo;
  private final PasswordHasher passwordHasher;

  @Inject
  public UserServiceImpl(UserRepository userRepo, PasswordHasher passwordHasher) {
    this.userRepo = userRepo;
    this.passwordHasher = passwordHasher;
  }

  @Override
  public User create() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Override
  public List<User> getAll() {
    return this.userRepo.getAll();
  }

  @Override
  public User getOne(int resourceId) {
    return this.userRepo.getOne(resourceId);
  }

  @Override
  public User update(int resourceId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public User delete(int resourceId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public User login(String email, String password) {
    User retrievedUser = this.userRepo.getByEmail(email);

    String hashForVerify = (retrievedUser != null)
        ? retrievedUser.getPassword()
        : "$argon2id$v=19$m=65536,t=3,p=1$c29tZXNhbHQ$6V/9iFJ8gY5u+0yWZKzJcJ6hA+3rEpuvhbnywqK8A5U";

    boolean isValid = this.passwordHasher.verify(hashForVerify, password.toCharArray());
    if (isValid)
      return retrievedUser;
    return null;
  }
}
