package com.example.appsmart.converter;

import com.example.appsmart.security.roles.UserPrincipal;
import com.example.appsmart.security.roles.UserRole;

public interface TokenService {
    String generateToken(UserRole user);

    UserPrincipal parseToken(String token);
}
