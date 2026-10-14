package org.xyz.productsvc.client.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
public class RestClientConfig {


    @Bean
    public RestClient userRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("https://jsonplaceholder.typicode.com")
                .defaultHeader("Content-Type", "application/json")
                .requestFactory(createRequestFactory(5, 10))
                .build();
    }

    @Bean
    @Qualifier("paymentRestClient")
    public RestClient paymentRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("https://paymentservice.com")
                .defaultHeader("Authorization", "Bearer INTERNAL_TOKEN")
                .requestFactory(createRequestFactory(2, 5))
                .build();
    }

    private JdkClientHttpRequestFactory createRequestFactory(int connectTimeoutSec, int readTimeoutSec) {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(connectTimeoutSec))
                .build();

        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(Duration.ofSeconds(readTimeoutSec));
        return factory;
    }
}
