package org.xyz.auth_svc.client;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xyz.auth_svc.client.model.GoogleUserInfo;

@RequestMapping("/v1")
public interface GoogleProfileClient {

    @GetExchange("/userinfo")
    GoogleUserInfo userinfo(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization
    );
}
