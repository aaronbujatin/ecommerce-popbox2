package org.xyz.chatsvc.client.model;

public record UserReq(
        String firstName,
        String lastName,
        int age
) {
}
