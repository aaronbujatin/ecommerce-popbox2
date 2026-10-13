package org.xyz.auth_svc.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KeycloakAccessTokenResp(
        @JsonProperty("access_token")
        String accessToken
) {
}
