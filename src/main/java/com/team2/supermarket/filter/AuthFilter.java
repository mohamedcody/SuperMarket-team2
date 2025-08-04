package com.team2.supermarket.filter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.supermarket.exception.ErrorResponse;
import com.team2.supermarket.service.Implementation.UserDetailsServiceImp;
import com.team2.supermarket.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImp userDetailsService;

    private List<String> whitelist;

    @PostConstruct
    public void init() {
        whitelist = new ArrayList<>();
        whitelist.add("/auth");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        for (String w : whitelist) {
            if (path.startsWith(w)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        try {
            String auth = request.getHeader("Authorization");

            if (auth == null || !auth.startsWith("Bearer")) {
                handleException(response, new RuntimeException("Missing or invalid Authorization header"));
                return;
            }

            String token = auth.substring(7);

            if (!jwtUtils.validateToken(token)) {
                handleException(response, new RuntimeException("Invalid Token"));
                return;
            }

            Claims claims = jwtUtils.getClaims(token);
            String username = claims.get("username").toString();
            String role = claims.get("role").toString();

            UserDetails user = userDetailsService.loadUserByUsername(username);

            if (user == null) {
                handleException(response, new RuntimeException("User not found"));
                return;
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    List.of(new SimpleGrantedAuthority(role))
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            handleException(response, new RuntimeException(e.getMessage()));
        }
    }

    private void handleException(HttpServletResponse response1, RuntimeException ex) throws IOException {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
        response1.setStatus(HttpStatus.FORBIDDEN.value());
        response1.setContentType("application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        String errorJson = objectMapper.writeValueAsString(error);

        response1.getWriter().write(errorJson);
    }

}
