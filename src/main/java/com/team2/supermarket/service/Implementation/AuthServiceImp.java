package com.team2.supermarket.service.Implementation;

import com.team2.supermarket.dto.Credentials;
import com.team2.supermarket.entity.CustomUser;
import com.team2.supermarket.repository.UserRepositoty;
import com.team2.supermarket.service.AuthService;
import com.team2.supermarket.utils.JwtUtils;

import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthServiceImp implements AuthService {


    private final UserRepositoty userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthServiceImp(UserRepositoty userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public String login(Credentials credentials){
        CustomUser user = userRepository.findByUsername(credentials.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(credentials.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtils.generateToken(user);
    }

    @Override
    public String register(Credentials credentials){
        if(userRepository.existsByUsername(credentials.username())){
            throw  new RuntimeException("User already exists");
        }

        CustomUser user = new CustomUser();
        user.setUsername(credentials.username());
        user.setPassword(passwordEncoder.encode(credentials.password()));

        userRepository.save(user);

        return jwtUtils.generateToken(user);
    }

}
