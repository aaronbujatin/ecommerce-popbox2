package org.xyz.productsvc.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatusCode;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiErrorResponse(
        String code,
        String message,
        Instant timestamp,
        HttpStatusCode statusCode
) {
}
