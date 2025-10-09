package org.example.user;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import org.example.user.controller.*;
import org.example.user.service.*;
import org.example.user.repository.*;

public class UserModule extends AbstractModule {
  @Override
  public void configure() {
    bind(UserController.class)
        .to(UserControllerImpl.class)
        .in(Singleton.class);

    bind(UserService.class)
        .to(UserServiceImpl.class)
        .in(Singleton.class);

    bind(UserRepository.class)
        .to(UserRepositoryMockImpl.class)
        .in(Singleton.class);
  }
}
