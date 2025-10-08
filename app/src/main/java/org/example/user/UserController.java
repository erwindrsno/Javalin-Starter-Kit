package org.example.user;

import io.javalin.apibuilder.CrudHandler;

public interface UserController extends CrudHandler {
  void login();
}
