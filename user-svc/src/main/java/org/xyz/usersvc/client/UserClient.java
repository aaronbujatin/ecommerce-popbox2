package org.xyz.usersvc.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.xyz.usersvc.client.model.UserReq;
import org.xyz.usersvc.client.model.UserResp;

import java.util.List;

public interface UserClient {

    @PostExchange("/users/add")
    UserResp addUser(@RequestBody UserReq userReq);

    @GetExchange("/users/{id}")
    UserResp getUser(@PathVariable Long id);

    @GetExchange("/users")
    List<UserResp> getAllUser();
}
