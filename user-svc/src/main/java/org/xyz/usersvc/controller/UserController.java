package org.xyz.usersvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xyz.usersvc.client.UserClient;
import org.xyz.usersvc.client.model.UserReq;
import org.xyz.usersvc.client.model.UserResp;
import org.xyz.usersvc.model.UserInfo;
import org.xyz.usersvc.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserClient userClient;
    private final UserService service;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserReq userReq) {
        var resp = userClient.addUser(userReq);
        return ResponseEntity.ok("User successfully saved " + resp.id());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResp> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userClient.getUser(id));
    }

    @GetMapping("/by-email/")
    public ResponseEntity<UserInfo> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.getUserByEmail(email));
    }



}
