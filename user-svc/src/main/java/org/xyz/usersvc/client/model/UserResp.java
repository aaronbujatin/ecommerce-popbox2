package org.xyz.usersvc.client.model;

public record UserResp(
        Long id,
        String firstName,
        String lastName,
        int age,
        String email
) {
}
