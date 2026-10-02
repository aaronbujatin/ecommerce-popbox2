package org.xyz.usersvc.client.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LoggingInterceptor  implements ClientHttpRequestInterceptor {
    private static final int MAX_LOG_CHARS = 2000;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution
    ) throws IOException {
        log.info("Sending API: [{} {}] [Headers: {}]",
                request.getMethod(),
                request.getURI(),
                request.getHeaders()
        );

        if (body.length > 0) {
            log.info("--> Body: {}", new String(body, StandardCharsets.UTF_8));
        }

        long start = System.currentTimeMillis();
        ClientHttpResponse response = execution.execute(request, body);
        long took = System.currentTimeMillis() - start;

        byte[] responseBody = StreamUtils.copyToByteArray(response.getBody());

        log.info("Receiving API: [{} {} ({} ms) body: {}]",
                response.getStatusCode(),
                request.getURI(),
                took,
                truncate(new String(responseBody, StandardCharsets.UTF_8))
        );

        return new BufferedResponse(response, responseBody);
    }

    private static String truncate(String s) {
        return s.length() > MAX_LOG_CHARS ? s.substring(0, MAX_LOG_CHARS) + "...[truncated]" : s;
    }

    private static class BufferedResponse implements ClientHttpResponse {

        private final ClientHttpResponse delegate;
        private final byte[] body;

        BufferedResponse(ClientHttpResponse delegate, byte[] body) {
            this.delegate = delegate;
            this.body = body;
        }

        @Override
        public HttpStatusCode getStatusCode() throws IOException {
            return delegate.getStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return delegate.getStatusText();
        }

        @Override
        public HttpHeaders getHeaders() {
            return delegate.getHeaders();
        }

        @Override
        public InputStream getBody() {
            return new ByteArrayInputStream(body);
        }

        @Override
        public void close() {
            delegate.close();
        }
    }
}