package com.example.appsmart.security;

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
public class TokenServiceImpl implements TokenService {
    private static final String JWT_SECRET = "DOBC3LqiM8Kw3vyveblj6GCW3M1HKihlxa__YKXdFpg";

    @Override
    public String generateToken(UserRole user) {
        Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
        Date expirationDate = Date.from(expirationTime);

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

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
        System.out.println(new TokenServiceImpl().generateToken(new UserRole(1, "username", "password", true)));
    }

    @Override
    public UserPrincipal parseToken(String token) {
        byte[] secretBytes = JWT_SECRET.getBytes();

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
