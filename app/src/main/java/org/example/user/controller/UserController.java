package org.example.user.controller;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;

public interface UserController extends CrudHandler {
  void login(Context ctx);
}
