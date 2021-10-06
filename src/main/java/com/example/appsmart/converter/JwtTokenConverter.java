package com.example.appsmart.converter;

import com.example.appsmart.Configuration;
import com.example.appsmart.security.roles.UserPrincipal;
import com.example.appsmart.security.roles.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtTokenConverter implements TokenService {

    @Override
    public String generateToken(UserRole user) {
        Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
        Date expirationDate = Date.from(expirationTime);

        Key key = Keys.hmacShaKeyFor(Configuration.JWT_SECRET.getBytes());

        String compactTokenString = Jwts.builder()
                .claim("id", user.getId())
                .claim("sub", user.getUsername())
                .claim("admin", user.isAdmin())
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();

        return "Bearer " + compactTokenString;
    }

    public static void main(String[] args) {
        System.out.println(new JwtTokenConverter().generateToken(new UserRole(1, "", "", true)));
    }

    @Override
    public UserPrincipal parseToken(String token) {
        byte[] secretBytes = Configuration.JWT_SECRET.getBytes();

        Jws<Claims> jwsClaims;
        jwsClaims = Jwts.parserBuilder()
                .setSigningKey(secretBytes)
                .build()
                .parseClaimsJws(token);
        String username = jwsClaims.getBody()
                .getSubject();
        Integer userId = jwsClaims.getBody()
                .get("id", Integer.class);
        boolean isAdmin = jwsClaims.getBody().get("admin", Boolean.class);

        return new UserPrincipal(userId, username, isAdmin);
    }
}
