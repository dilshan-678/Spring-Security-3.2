package com.SpringSecurity.Spring.Security.service;

import com.SpringSecurity.Spring.Security.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;

public interface JWTService {

     String extractUserName(String token);
     String generateToken(UserDetails userDetails);
     boolean isTokenValid(String token,UserDetails userDetails);

     String generateRefreshToken(HashMap<String, Object> extraClaims, UserDetails userDetails);
}
