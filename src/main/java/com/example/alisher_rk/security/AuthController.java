package com.example.alisher_rk.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${app.security.token}")
    private String token;

    @GetMapping("/token")
    public String token() {
        return token;
    }
}