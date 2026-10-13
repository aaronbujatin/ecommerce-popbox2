package org.xyz.auth_svc.model;

import lombok.Builder;

@Builder
public record UserInfo(
        String firstName,
        String lastName,
        String email
) {
}
