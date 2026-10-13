package org.xyz.cartsvc.client.config;

import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xyz.cartsvc.client.util.FeignClientLogger;
import org.xyz.cartsvc.client.util.FeignErrorDecoder;

@Configuration
public class FeignClientConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    Logger feignClientLogger() {
        return new FeignClientLogger();
    }

    @Bean
    public ErrorDecoder customFeignErrorDecoder() {
        return new FeignErrorDecoder();
    }

}