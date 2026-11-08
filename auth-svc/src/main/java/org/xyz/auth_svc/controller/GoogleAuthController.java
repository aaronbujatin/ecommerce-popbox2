package org.xyz.auth_svc.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xyz.auth_svc.client.config.GoogleProperties;
import org.xyz.auth_svc.service.GoogleAuthService;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth/google")
public class GoogleAuthController {

    private final GoogleAuthService googleAuthService;
    private final GoogleProperties props;

    /**
     * Starts the login: 302 redirect to the Google consent screen.
     * The frontend only needs to link / navigate to this endpoint.
     */
    @GetMapping
    public ResponseEntity<Void> login() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(googleAuthService.buildLoginUrl()))
                .build();
    }

    /**
     * Browser return point (must match GOOGLE_REDIRECT_URI / the redirect URI registered
     * in the Google Cloud console). Sets the session cookie and bounces the user back
     * to the frontend success URL either way, with a googleError param on failure.
     */
    @GetMapping("/callback")
    public ResponseEntity<Void> callback(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String error,
            HttpServletResponse res
    ) {
        String target;
        if (!isBlank(error)) {
            log.warn("Google login rejected by user or error param from Google: {}", error);
            target = props.successRedirect() + "?googleError=" + error;
        } else {
            try {
                googleAuthService.completeLogin(code, state, res);
                target = props.successRedirect();
            } catch (Exception e) {
                log.error("Google login failed", e);
                target = props.successRedirect() + "?googleError=login_failed";
            }
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(target))
                .build();
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
