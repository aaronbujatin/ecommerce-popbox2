package org.xyz.auth_svc.model;

public record VerificationCodeRequest(
        String code,
        String email
) {
}
