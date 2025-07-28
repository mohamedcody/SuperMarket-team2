package com.team2.supermarket.utils;
import com.team2.supermarket.entity.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.expire}")
    private Long expireAfter;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(CustomUser customuser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", customuser.getId());
        claims.put("username", customuser.getUsername());
        claims.put("role", customuser.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(customuser.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireAfter))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getRoleFormToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("role").toString();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        Claims claims = getClaims(token);

        if (!claims.containsKey("username")) {
            return false;
        }

        Date iat = claims.getIssuedAt();
        Date exp = claims.getExpiration();

        return !(new Date().before(iat) || new Date().after(exp));



}
}
