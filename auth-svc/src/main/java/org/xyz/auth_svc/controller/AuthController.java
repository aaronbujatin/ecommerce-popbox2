package org.xyz.auth_svc.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xyz.auth_svc.model.EmailRequest;
import org.xyz.auth_svc.model.VerificationCodeRequest;
import org.xyz.auth_svc.service.OtpService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final OtpService otpService;

    @PostMapping("/request-code")
    public ResponseEntity<?> requestCode(@RequestBody EmailRequest req) {
        otpService.sendOtp(req.email());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verify(@RequestBody VerificationCodeRequest req, HttpServletResponse res) throws BadRequestException {

        return ResponseEntity.ok(otpService.verifyCode(req, res));
    }

//    @GetMapping("/me")
//    public UserDto me(Authentication auth) {
//        User u = userRepo.findByEmail(auth.getName()).orElseThrow();
//        return new UserDto(u.getId(), u.getEmail(), u.getFullName());
//    }

}
