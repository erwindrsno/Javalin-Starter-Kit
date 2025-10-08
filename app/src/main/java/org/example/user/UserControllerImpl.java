package org.example.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.javalin.http.Context;

public class UserControllerImpl implements UserController {
  private final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

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
    logger.info("Entered get all");
  }

  @Override
  public void getOne(Context ctx, String arg1) {
    logger.info("Entered get getONE");
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
