package org.xyz.cartsvc.dto;

import org.springframework.http.HttpStatusCode;

import java.time.Instant;

public record ApiErrorResponse(
        String code,
        String message,
        Instant timestamp,
        HttpStatusCode statusCode
) {
}