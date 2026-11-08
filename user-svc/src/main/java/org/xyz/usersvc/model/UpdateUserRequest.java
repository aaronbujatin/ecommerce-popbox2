package org.xyz.usersvc.model;

public record UpdateUserRequest(
        String email,
        String password,
        String firstName,
        String lastName,
        String phone
) {
}
