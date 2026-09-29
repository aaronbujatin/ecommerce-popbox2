package org.xyz.productsvc.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xyz.productsvc.dto.ApiErrorResponse;
import org.xyz.productsvc.exception.ResourceNotFoundException;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(
            Exception ex) {

        var apiErrorResponse = new ApiErrorResponse(
                null,
                ex.getMessage(),
                Instant.now(),
                HttpStatus.SERVICE_UNAVAILABLE
        );

        logApiErrorResponse(apiErrorResponse);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiErrorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(
                ResourceNotFoundException ex) {

        var apiErrorResponse = new ApiErrorResponse(
                ex.getProductErrorInfo().getCode(),
                ex.getMessage(),
                Instant.now(),
                HttpStatus.NOT_FOUND
        );

        logApiErrorResponse(apiErrorResponse);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiErrorResponse);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        var apiErrorResponse = new ApiErrorResponse(
                "PR101",
                ex.getBindingResult().getFieldError().getDefaultMessage(),
                Instant.now(),
                HttpStatus.BAD_REQUEST
        );

        logApiErrorResponse(apiErrorResponse);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiErrorResponse);
    }

    private void logApiErrorResponse(ApiErrorResponse apiErrorResponse) {
        log.warn("Exception occur, returning error response : {}", apiErrorResponse);
    }

}
