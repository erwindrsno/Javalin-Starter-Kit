package org.example.main;

import org.example.user.UserController;
import org.example.user.UserModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Server {
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(Server.class);
    Dotenv dotenv = Dotenv.configure().load();
    final int PORT = Integer.parseInt(dotenv.get("PORT"));

    Injector injector = Guice.createInjector(
        new MainModule(),
        new UserModule());
    UserController userCtrl = injector.getInstance(UserController.class);

    Javalin app = Javalin.create(config -> {
      config.router.apiBuilder(() -> {
        path("/users", () -> {
          crud("/{id}", userCtrl);
        });
      });
    });
    logger.info("Server is started at port 7070");
    app.start(PORT);

  }
}
