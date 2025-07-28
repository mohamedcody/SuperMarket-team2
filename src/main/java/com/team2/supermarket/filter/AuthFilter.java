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
import org.springframework.data.mongodb.core.ReadPreferenceAware;
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
    JwtUtils jwtUtils ;
    @Autowired
    UserDetailsServiceImp userDetailsService;

    private final ReadPreferenceAware readPreferenceAware ;
    private List<String> whitelist  ;

    public AuthFilter(ReadPreferenceAware readPreferenceAware) {
        this.readPreferenceAware = readPreferenceAware;
    }
    public void inti(){
        whitelist = new ArrayList<>();
        whitelist.add("/auth");
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.err.println("auth filter");
        String path  = request.getServletPath();
        for(String w : whitelist ){
            if(path.startsWith(w)){
                filterChain.doFilter(request, response );
                return ;
            }
        }
        try {
            String auth = request.getHeader("Authorization");

            if (!auth.startsWith("Bearer")) {
                handelException(response, new RuntimeException("missing token"));
            }
            String token = auth.substring(7);
            if (!jwtUtils.validateToken(token)) {
                handelException(response, new RuntimeException("invalid Token "));
            }
            Claims claims = jwtUtils.getClaims(token);
            UserDetails user = userDetailsService.loadUserByUsername(claims.get("username").toString());

            if (user == null) {
                handelException(response, new RuntimeException("user not found "));
            }
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user, null, List.of(new SimpleGrantedAuthority(claims.get("role").toString()
            ))
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handelException(response, new RuntimeException(e.getMessage()));
        }

    }



    private void handelException(HttpServletResponse response1 , RuntimeException ex ) throws IOException {

        ErrorResponse response = new ErrorResponse(ex.getMessage() , HttpStatus.FORBIDDEN );

        response1.setStatus(HttpStatus.FORBIDDEN.value());

        System.out.println(ex.getMessage());

        ObjectMapper objectMapper = new ObjectMapper();
        String errerJson = objectMapper.writeValueAsString(response);

        response1.getWriter().write(errerJson);
    }

}
