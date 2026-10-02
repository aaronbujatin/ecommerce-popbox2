package org.xyz.usersvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xyz.usersvc.client.UserClient;
import org.xyz.usersvc.client.model.UserResp;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserClient userClient;

    @GetMapping("/{id}")
    public ResponseEntity<UserResp> getUserById(@PathVariable Long id) {
        System.out.println("TEST HERE");
        return ResponseEntity.ok(userClient.getUser(id));
    }

    @GetMapping()
    public ResponseEntity<String> getUserById2(@PathVariable Long id) {
        System.out.println("TEST HERE");
        return ResponseEntity.ok("TESTHRER");
    }



}
