package org.example.main;

import org.example.user.UserController;
import org.example.user.UserModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.inject.Guice;
import com.google.inject.Injector;

import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Server {
  public static void main(String[] args) {
    Dotenv dotenv = Dotenv.configure().load();
    final int PORT = Integer.parseInt(dotenv.get("PORT"));

    Injector injector = Guice.createInjector(
        new MainModule(),
        new UserModule());
    UserController userCtrl = injector.getInstance(UserController.class);

    Javalin app = Javalin.create(config -> {
      config.jsonMapper(new JavalinJackson().updateMapper(mapper -> {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      }));
      config.router.apiBuilder(() -> {
        path("/users", () -> {
          crud("/{id}", userCtrl);
        });
      });
    });
    app.start(PORT);
  }
}
