package org.xyz.usersvc.model;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String phone
) {
}
