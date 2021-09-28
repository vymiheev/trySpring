package com.example.appsmart.security;

public interface TokenService {
    String generateToken(UserRole user);

    UserPrincipal parseToken(String token);
}
