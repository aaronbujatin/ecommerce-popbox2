package org.xyz.auth_svc.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.xyz.auth_svc.client.KeycloakClient;
import org.xyz.auth_svc.client.UserClient;
import org.xyz.auth_svc.client.model.UserReq;
import org.xyz.auth_svc.model.UserInfo;
import org.xyz.auth_svc.model.VerificationCodeRequest;

import java.security.SecureRandom;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class OtpService {

    private final StringRedisTemplate redis;
    private final JavaMailSender mailSender;
    private final UserClient userClient;
    private final KeycloakClient keycloakClient;

    public void sendOtp(String email) {
        String code = String.format("%06d", new SecureRandom().nextInt(1_000_000));
        redis.opsForValue().set("otp:" + email, code, Duration.ofMinutes(5));

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Your Pop Box profile code");
            helper.setText(otpHtmlContent(code), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }

    public UserInfo verifyCode(VerificationCodeRequest request,
                               HttpServletResponse res
    ) throws BadRequestException {
        String key = "otp:" + request.email();
        String saved = redis.opsForValue().get(key);
        if (saved != null && saved.equals(request.code())) {
            redis.delete(key);
        } else throw new BadRequestException("invalid verification code");

        var user = userClient.getUserByEmail(request.email())
                .orElseGet(() -> userClient.createUser(UserReq.builder()
                        .email(request.email())
                        .build())
                );

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", "CLIENT002");
        formData.add("client_secret", "3tc2a5mcQS47n7Ft4lebpX4AGOdZuWvu");
        formData.add("grant_type", "client_credentials");

        var keycloakResp = keycloakClient.token(formData);

        String token = keycloakResp.accessToken();

        ResponseCookie cookie = ResponseCookie.from("session", token)
                .httpOnly(true).secure(true).sameSite("Lax")
                .path("/").maxAge(Duration.ofDays(7)).build();
        res.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return new UserInfo(user.firstName(), user.lastName(), user.email());

    }

    public boolean verify2(String email, String code) {
        String key = "otp:" + email;
        String saved = redis.opsForValue().get(key);
        if (saved != null && saved.equals(code)) {
            redis.delete(key);   // one-time use
            return true;
        }
        return false;
    }

    private String otpHtmlContent(String code) {
        return """
        <!DOCTYPE html>
           <html lang="en">
           <head>
             <meta charset="UTF-8">
             <meta name="viewport" content="width=device-width, initial-scale=1.0">
             <title>Your Pop Box Verification Code</title>
             <style>
               body {
                 margin: 0;
                 padding: 0;
                 background-color: #ffffff;
                 font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
                 -webkit-font-smoothing: antialiased;
               }
               .email-container {
                 max-width: 480px;
                 margin: 0 auto;
                 padding: 40px 20px;
                 text-align: center;
               }
               .logo {
                 width: 90px;
                 height: auto;
                 margin-bottom: 30px;
               }
               .title {
                 font-size: 22px;
                 font-weight: 600;
                 color: #111111;
                 margin: 0 0 10px 0;
               }
               .subtitle {
                 font-size: 14px;
                 color: #666666;
                 margin: 0 0 25px 0;
                 line-height: 1.4;
               }
               .divider {
                 border: 0;
                 border-top: 1px solid #e5e5e5;
                 margin: 25px 0;
               }
               .code-container {
                 padding: 15px 0;
               }
               .code {
                 font-size: 36px;
                 font-weight: 700;
                 letter-spacing: 4px;
                 color: #E61C30; /* Pop Box Primary Red */
                 margin: 0;
               }
               .expiry-text {
                 font-size: 13px;
                 font-weight: 600;
                 color: #111111;
                 margin: 0 0 8px 0;
               }
               .disclaimer-text {
                 font-size: 12px;
                 color: #7E7E7E;
                 line-height: 1.5;
                 margin: 0;
               }
               .brand-link {
                 font-size: 16px;
                 font-weight: 700;
                 color: #22252A; /* Pop Box Dark Charcoal */
                 text-decoration: none;
                 display: inline-block;
                 margin: 10px 0;
               }
               .footer-text {
                 font-size: 11px;
                 color: #8D8D8D;
                 line-height: 1.6;
                 margin: 0 0 15px 0;
               }
               .footer-links a {
                 font-size: 11px;
                 color: #8D8D8D;
                 text-decoration: underline;
                 margin: 0 8px;
               }
             </style>
           </head>
           <body>
    
             <table role="presentation" width="100%" cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff">
               <tr>
                 <td align="center">
                   <div class="email-container">
                    \s
                     <!-- Pop Box Logo -->
                     <img src="YOUR_HOSTED_POPBOX_LOGO_URL.png" alt="Pop Box Logo" class="logo" />
    
                     <!-- Heading -->
                     <h1 class="title">Your Pop Box profile code</h1>
                     <p class="subtitle">Here's the one-time verification code you requested:</p>
    
                     <hr class="divider" />
    
                     <div class="code-container">
                       <h2 class="code">%s</h2>
                     </div>
                     <hr class="divider" />
                     <p class="expiry-text">This code expires after 15 minutes.</p>
                     <p class="disclaimer-text">
                       If you've already received this code or don't need it any more, ignore this email.
                     </p>
                     <hr class="divider" />
                     <a href="https://popbox.com" class="brand-link">PopBox.com</a>
                     <p class="footer-text">
                       &copy; 2026 Pop Box, Inc. All Rights Reserved.<br />
                       Pop Box Operations, 123 Pop Street, Suite 400, Manila, Philippines
                     </p>
                     <div class="footer-links">
                       <a href="#">Privacy Policy</a>
                       <a href="#">Get Help</a>
                     </div>
                   </div>
                 </td>
               </tr>
             </table>
           </body>
           </html>
        """.formatted(code);
    }
}
