package org.example.user.repository;

import org.example.main.CrudRepository;
import org.example.user.User;

public interface UserRepository extends CrudRepository<User> {
  User getByEmail(String email);
}
