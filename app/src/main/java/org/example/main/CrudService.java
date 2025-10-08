package org.example.main;

import java.util.List;

public interface CrudService<T> {
  List<T> getAll();

  T getOne(int resourceId);

  T create();

  T update(int resourceId);

  T delete(int resourceId);
}
