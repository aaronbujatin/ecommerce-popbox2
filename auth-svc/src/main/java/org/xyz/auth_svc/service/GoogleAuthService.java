package org.xyz.auth_svc.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.xyz.auth_svc.client.GoogleAuthClient;
import org.xyz.auth_svc.client.GoogleProfileClient;
import org.xyz.auth_svc.client.KeycloakClient;
import org.xyz.auth_svc.client.UserClient;
import org.xyz.auth_svc.client.config.GoogleProperties;
import org.xyz.auth_svc.client.model.GoogleTokenResp;
import org.xyz.auth_svc.client.model.GoogleUserInfo;
import org.xyz.auth_svc.client.model.UserReq;
import org.xyz.auth_svc.model.UserInfo;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.HexFormat;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoogleAuthService {

    private static final String AUTHORIZE_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String SCOPE = "openid email profile";
    private static final String STATE_KEY_PREFIX = "google:state:";

    private final GoogleProperties props;
    private final StringRedisTemplate redis;
    private final GoogleAuthClient googleAuthClient;
    private final GoogleProfileClient googleProfileClient;
    private final UserClient userClient;
    private final KeycloakClient keycloakClient;

    /**
     * Step 1: builds the Google consent URL and stores a one-time state in Redis (CSRF protection).
     * The frontend simply navigates the browser to GET /api/v1/auth/google.
     */
    public String buildLoginUrl() {
        if (isBlank(props.clientId()) || isBlank(props.redirectUri())) {
            throw new IllegalStateException("Google OAuth is not configured: set GOOGLE_CLIENT_ID and GOOGLE_REDIRECT_URI");
        }

        String state = newToken();
        redis.opsForValue().set(STATE_KEY_PREFIX + state, "1", Duration.ofMinutes(10));

        return UriComponentsBuilder.fromUriString(AUTHORIZE_URL)
                .queryParam("client_id", props.clientId())
                .queryParam("redirect_uri", props.redirectUri())
                .queryParam("response_type", "code")
                .queryParam("scope", SCOPE)
                .queryParam("state", state)
                .queryParam("prompt", "select_account")
                .encode()
                .toUriString();
    }

    /**
     * Step 2: called from GET /api/v1/auth/google/callback on the browser's way back from Google.
     * Validates the state, exchanges the code, loads the Google profile, upserts the user
     * in the user service, and issues the same "session" cookie as the OTP flow.
     *
     * @return the user info of the logged-in user
     */
    public UserInfo completeLogin(String code, String state, HttpServletResponse res) {
        if (isBlank(code) || isBlank(state)) {
            throw new IllegalArgumentException("Missing code or state parameter");
        }
        if (isBlank(props.clientSecret())) {
            throw new IllegalStateException("Google OAuth is not configured: set GOOGLE_CLIENT_SECRET");
        }

        // state is single-use: delete() returns true only if the key was present and not expired
        Boolean validState = redis.delete(STATE_KEY_PREFIX + state);
        if (!Boolean.TRUE.equals(validState)) {
            throw new IllegalStateException("Invalid or expired state");
        }

        GoogleTokenResp token = googleAuthClient.token(authorizationCodeForm(code));
        if (token == null || isBlank(token.accessToken())) {
            throw new IllegalStateException("Google token exchange failed");
        }

        GoogleUserInfo profile = googleProfileClient.userinfo("Bearer " + token.accessToken());
        if (profile == null || isBlank(profile.email())) {
            throw new IllegalStateException("Google did not return an email address");
        }

        var user = userClient.getUserByEmail(profile.email())
                .orElseGet(() -> userClient.createUser(UserReq.builder()
                        .email(profile.email())
                        .firstName(profile.givenName())
                        .lastName(profile.familyName())
                        .build())
                );

        var keycloakResp = keycloakClient.token(clientCredentialsForm());

        ResponseCookie cookie = ResponseCookie.from("session", keycloakResp.accessToken())
                .httpOnly(true).secure(true).sameSite("Lax")
                .path("/").maxAge(Duration.ofDays(7)).build();
        res.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        log.info("Google login completed for email={}", profile.email());

        return new UserInfo(user.firstName(), user.lastName(), user.email());
    }

    private MultiValueMap<String, String> authorizationCodeForm(String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", props.clientId());
        formData.add("client_secret", props.clientSecret());
        formData.add("code", code);
        formData.add("redirect_uri", props.redirectUri());
        formData.add("grant_type", "authorization_code");
        return formData;
    }

    // TODO: mirror of the credentials hardcoded in OtpService.verifyCode() -> move to configuration
    private MultiValueMap<String, String> clientCredentialsForm() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", "CLIENT002");
        formData.add("client_secret", "3tc2a5mcQS47n7Ft4lebpX4AGOdZuWvu");
        formData.add("grant_type", "client_credentials");
        return formData;
    }

    private static String newToken() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return HexFormat.of().formatHex(bytes);
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
