package org.example.util;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import org.example.util.argon.ArgonHasher;

public class UtilityModule extends AbstractModule {
  @Override
  public void configure() {
    bind(PasswordHasher.class)
        .to(ArgonHasher.class)
        .in(Singleton.class);
  }
}
