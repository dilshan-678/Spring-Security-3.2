package com.SpringSecurity.Spring.Security.service.impl;
import com.SpringSecurity.Spring.Security.model.User;
import com.SpringSecurity.Spring.Security.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

    @Value("${application.jwt.secretkey}")
    private String secretKey;


    public String generateToken(UserDetails userDetails){

        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigniKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserName(String token){

        return extractClaim(token,Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims , T> claimsResolvers){
        
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder().setSigningKey(getSigniKey())
                .build().parseClaimsJws(token).getBody();

    }

    private Key getSigniKey() {

        byte[] key = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(key);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){

        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String generateRefreshToken(HashMap<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSigniKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token ,Claims::getExpiration).before(new Date());
    }
}
