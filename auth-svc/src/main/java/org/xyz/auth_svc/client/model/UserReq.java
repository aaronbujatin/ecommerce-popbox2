package org.xyz.auth_svc.client.model;

import lombok.Builder;

@Builder
public record UserReq(
        String email,
        String firstName,
        String lastName,
        int age
) {
}
