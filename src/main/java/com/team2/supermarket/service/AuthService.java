package com.team2.supermarket.service;

import com.team2.supermarket.dto.Credentials;

public interface AuthService {
    public String login(Credentials credentials);
    public String register(Credentials credentials);
}
