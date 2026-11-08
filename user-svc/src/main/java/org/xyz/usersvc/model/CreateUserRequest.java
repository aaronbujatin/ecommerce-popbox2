package org.xyz.usersvc.model;

public record CreateUserRequest(
        String email,
        String password,
        String firstName,
        String lastName,
        String phone
) {
}
