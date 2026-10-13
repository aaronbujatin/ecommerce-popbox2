package org.xyz.auth_svc.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.xyz.auth_svc.client.model.UserReq;
import org.xyz.auth_svc.client.model.UserResp;


import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/users")
public interface UserClient {

    @PostExchange("/create")
    UserResp createUser(@RequestBody UserReq userReq);

    @GetExchange("/{id}")
    UserResp getUser(@PathVariable Long id);

    @GetExchange("/by-email/{email}")
    Optional<UserResp> getUserByEmail(@PathVariable String email);

    @GetExchange()
    List<UserResp> getAllUser();
}
