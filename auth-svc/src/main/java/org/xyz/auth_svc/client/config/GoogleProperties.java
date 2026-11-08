package org.xyz.auth_svc.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.oauth.google")
public record GoogleProperties(
        String clientId,
        String clientSecret,
        String redirectUri,
        String successRedirect
) {
}
