package org.xyz.chatsvc.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.xyz.chatsvc.client.model.TxnReqPayload;

import java.util.List;

public interface FastekClient {

    @PostExchange("/pay/card/checkout")
    void checkout(@RequestBody TxnReqPayload reqPayload);

//    @GetExchange("/users/{id}")
//    UserResp getUser(@PathVariable Long id);
//
//    @GetExchange("/users")
//    List<UserResp> getAllUser();
}
