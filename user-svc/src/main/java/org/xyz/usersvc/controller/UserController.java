package org.xyz.usersvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xyz.usersvc.client.UserClient;
import org.xyz.usersvc.client.model.UserReq;
import org.xyz.usersvc.client.model.UserResp;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserReq userReq) {
        var resp = userClient.addUser(userReq);
        return ResponseEntity.ok("User successfully saved " + resp.id());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResp> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userClient.getUser(id));
    }


}
