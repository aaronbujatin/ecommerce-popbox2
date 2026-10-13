package org.xyz.cartsvc.client.util;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        var status = HttpStatus.valueOf(response.status());
        var responseBody = extractResponseBody(response);

        log.error("feign client error. Method: {}, Status: {}, Body: {}",
                methodKey, status, responseBody
        );

        switch (status){
            case BAD_REQUEST:
                return new IllegalArgumentException("invalid request");
            case UNAUTHORIZED:
                return new SecurityException("unauthorized request");
            default:
                return new Exception("unexpected error: " + responseBody);


        }
    }

    private String extractResponseBody(Response response) {
        if (response.body() == null) {
            return "no response body";
        }

        try {
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);

        } catch (IOException e) {
//            throw new RuntimeException(e);
            log.error("failed to read response body {}", e.getLocalizedMessage());
            return "error reading response body";
        }
    }

}
