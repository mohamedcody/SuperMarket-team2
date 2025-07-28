package com.team2.supermarket.service.Implementation;

import com.team2.supermarket.entity.CustomUser;
import com.team2.supermarket.exception.NotFoundException;
import com.team2.supermarket.repository.UserRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

  @Autowired
    UserRepositoty userRepository ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser user = userRepository.findByUsername(username).orElseThrow(
                ()->new NotFoundException("user not found ")
        );
        return User.
                builder().
                username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
    }
