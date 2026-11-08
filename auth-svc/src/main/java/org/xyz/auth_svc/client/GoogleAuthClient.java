package org.xyz.auth_svc.client;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xyz.auth_svc.client.model.GoogleTokenResp;

@RequestMapping("/")
public interface GoogleAuthClient {

    @PostExchange("/token")
    GoogleTokenResp token(
            @RequestParam MultiValueMap<String, String> formData
    );
}
