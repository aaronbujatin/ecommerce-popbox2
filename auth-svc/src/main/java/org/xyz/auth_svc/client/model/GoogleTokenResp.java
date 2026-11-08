package org.xyz.auth_svc.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleTokenResp(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("id_token") String idToken,
        @JsonProperty("expires_in") long expiresIn,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("scope") String scope
) {
}
