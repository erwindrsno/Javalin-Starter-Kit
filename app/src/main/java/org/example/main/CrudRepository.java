package org.example.main;

import java.util.List;

public interface CrudRepository<T> {
  List<T> getAll();

  T getOne(int resourceId);

  T create(T item);

  T update(int resourceId, T item);

  T delete(int resourceId);
}
