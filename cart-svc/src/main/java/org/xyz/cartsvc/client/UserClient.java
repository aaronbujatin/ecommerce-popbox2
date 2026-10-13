package org.xyz.cartsvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.xyz.cartsvc.client.config.FeignClientConfig;
import org.xyz.cartsvc.client.dto.UserResponse;

import java.util.Optional;

@FeignClient(name = "user-service", url = "http://localhost:8083", configuration = FeignClientConfig.class)
public interface UserClient {

    @GetMapping("/api/v1/customers/{id}")
    Optional<UserResponse> getUserById(@PathVariable("id") Long id);

}
