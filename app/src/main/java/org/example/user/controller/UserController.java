package org.example.user.controller;

import io.javalin.apibuilder.CrudHandler;

public interface UserController extends CrudHandler {
  void login();
}
