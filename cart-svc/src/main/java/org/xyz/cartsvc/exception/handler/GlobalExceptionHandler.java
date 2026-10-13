package org.xyz.cartsvc.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xyz.cartsvc.dto.ApiErrorResponse;
import org.xyz.cartsvc.enums.CartErrorInfo;
import org.xyz.cartsvc.exception.CartException;
import org.xyz.cartsvc.exception.InvalidCartRequestException;
import org.xyz.cartsvc.exception.ResourceNotFoundException;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
                {
                    ResourceNotFoundException.class,
                }
            )
    public ResponseEntity<ApiErrorResponse> handleCartExceptions(
            CartException ex,
            HttpServletRequest request
    ) {
        CartErrorInfo productErrorInfo = ex.getCartErrorInfo();
        ApiErrorResponse apiErrorResponse =
                new ApiErrorResponse(
                    productErrorInfo.getCode(),
                    productErrorInfo.getMessage(),
                    Instant.now(),
                    HttpStatus.NOT_FOUND
                );

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(apiErrorResponse);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
    })
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        var apiErrorResponse = new ApiErrorResponse(
                ex.getMessage(),
                ex.getBindingResult().getFieldError().getDefaultMessage(),
                Instant.now(),
                HttpStatus.BAD_REQUEST
        );

        logApiErrorResponse(apiErrorResponse);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiErrorResponse);
    }

    @ExceptionHandler({
            InvalidCartRequestException.class
    })
    public ResponseEntity<ApiErrorResponse> handleInvalidCartRequestException(
            InvalidCartRequestException ex) {

        var apiErrorResponse = new ApiErrorResponse(
                ex.getCartErrorInfo().getCode(),
                ex.getCartErrorInfo().getMessage(),
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