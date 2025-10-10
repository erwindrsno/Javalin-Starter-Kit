package org.example.user.service;

import org.example.main.CrudService;
import org.example.user.User;

public interface UserService extends CrudService<User> {
  User login(String email, String password);
}
