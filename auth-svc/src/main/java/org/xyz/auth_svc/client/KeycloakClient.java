package org.xyz.auth_svc.client;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.PostExchange;
import org.xyz.auth_svc.client.model.KeycloakAccessTokenResp;
import org.xyz.auth_svc.client.model.UserReq;
import org.xyz.auth_svc.client.model.UserResp;

@RequestMapping("/realms/popbox2/protocol/openid-connect")
public interface KeycloakClient {

    @PostExchange("/token")
    KeycloakAccessTokenResp token(
            @RequestParam MultiValueMap<String, String> formData
    );
}
