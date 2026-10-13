package org.xyz.cartsvc.client.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HttpClientLogger {


//    public static void logRequest(HttpRequest request, byte[] body) {
//        log.info("Sending Api ➡️ [Method: {}] [URL: {}] [Header: {}] [Request body: {}]",
//                request.getMethod(),
//                request.getURI(),
//                request.getHeaders(),
//                new String(body, StandardCharsets.UTF_8)
//        );
//    }
//
//    public static void logResponse(ClientHttpResponse response) throws IOException {
//        log.info("Receiving API ➡️ [Status: {}] [Header: {}] [Request body: {}]",
//                response.getStatusCode(),
//                response.getHeaders(),
//                new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8)
//        );
//    }
}
