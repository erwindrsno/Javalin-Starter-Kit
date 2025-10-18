package org.example.user.service;

interface UserDTO {
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
