package org.example.user;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import org.example.main.ApiResponse;

import io.javalin.http.Context;

public class UserControllerImpl implements UserController {
  private final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);
  private final UserService userService;

  @Inject
  public UserControllerImpl(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void create(Context ctx) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Override
  public void delete(Context ctx, String arg1) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public void getAll(Context ctx) {
    List<User> usersList = this.userService.getAll();
    ctx.json(usersList);
  }

  @Override
  public void getOne(Context ctx, String arg0) {
    User user = this.userService.getOne(Integer.parseInt(arg0));
    ctx.json(new ApiResponse<>(
        user,
        Map.of(
            "requestedAt", System.currentTimeMillis())));
  }

  @Override
  public void update(Context ctx, String arg1) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void login() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'login'");
  }
}
