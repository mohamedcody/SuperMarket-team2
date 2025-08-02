package com.team2.supermarket.controller;
import com.team2.supermarket.dto.Credentials;
import com.team2.supermarket.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public String login(@RequestBody Credentials credentials) {
        return service.login(credentials);
    }

    @PostMapping("/register")
    public String register(@RequestBody Credentials credentials) {
        return service.register(credentials);
    }







}
