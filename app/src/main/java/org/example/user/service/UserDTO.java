package org.example.user.service;

//DTO interface being put here is for encapsulation purpose
public sealed interface UserDTO permits LoginRequest, RegisterRequest {
}

record LoginRequest(
    String email,
    String password)
    implements UserDTO {
}

record RegisterRequest(
    String username,
    String email,
    String password,
    int roleId)
    implements UserDTO {
}
