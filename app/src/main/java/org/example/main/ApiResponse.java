package org.example.main;

public class ApiResponse<T> {
  private T data;
  private Object meta;

  public ApiResponse(T data, Object meta) {
    this.data = data;
    this.meta = meta;
  }

  public T getData() {
    return data;
  }

  public Object getMeta() {
    return meta;
  }
}
