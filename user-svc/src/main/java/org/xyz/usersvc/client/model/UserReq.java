package org.xyz.usersvc.client.model;

public record UserReq(
        String firstName,
        String lastName,
        int age
) {
}
