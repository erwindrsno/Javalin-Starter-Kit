package org.example.user;

import java.util.List;

import com.google.inject.Inject;

public class UserServiceImpl implements UserService {
  private final UserRepository userRepo;

  @Inject
  public UserServiceImpl(UserRepository userRepo) {
    this.userRepo = userRepo;
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
}
