package org.xyz.usersvc.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.registry.ImportHttpServices;
import org.xyz.usersvc.client.UserClient;
import org.xyz.usersvc.client.logging.LoggingInterceptor;
import org.xyz.usersvc.client.model.UserResp;

@Configuration
@ImportHttpServices(group = "user", types = UserClient.class)
public class HttpClientConfig {

    @Bean
    public RestClientHttpServiceGroupConfigurer groupConfigurer() {
        return groups -> {
            groups.filterByName("user")
                    .forEachClient((group, builder) ->
                            builder.defaultHeader("X-API-KEY", "secret"));

            groups.forEachClient((group, builder) ->
                    builder.requestInterceptor(new LoggingInterceptor()));
        };
    }
}
