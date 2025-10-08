package org.example.user;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class UserModule extends AbstractModule {
  @Override
  public void configure() {
    bind(UserController.class)
        .to(UserControllerImpl.class)
        .in(Singleton.class);
  }
}
