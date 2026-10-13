package org.xyz.chatsvc.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.registry.ImportHttpServices;
import org.xyz.chatsvc.client.FastekClient;
import org.xyz.chatsvc.client.logging.LoggingInterceptor;
import org.xyz.usersvc.client.UserClient;

@Configuration
@ImportHttpServices(group = "user", types = FastekClient.class)
public class HttpClientConfig {

    @Bean
    public RestClientHttpServiceGroupConfigurer groupConfigurer() {
        return groups -> {
            groups.filterByName("fastek")
                    .forEachClient((group, builder) ->
                            builder.defaultHeader("X-API-KEY", "secret"));

            groups.forEachClient((group, builder) ->
                    builder.requestInterceptor(new LoggingInterceptor()));
        };
    }
}
