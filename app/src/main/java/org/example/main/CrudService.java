package org.example.main;

import java.util.List;

public interface CrudService<T, R> {
  List<R> getAll();

  R getOne(int resourceId);

  R create(T data);

  R update(int resourceId);

  R delete(int resourceId);
}
