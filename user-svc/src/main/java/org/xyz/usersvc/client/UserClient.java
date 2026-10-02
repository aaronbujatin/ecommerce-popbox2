package org.xyz.usersvc.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.xyz.usersvc.client.model.UserResp;

public interface UserClient {

    @GetExchange("/users/{id}")
    UserResp getUser(@PathVariable Long id);

}
