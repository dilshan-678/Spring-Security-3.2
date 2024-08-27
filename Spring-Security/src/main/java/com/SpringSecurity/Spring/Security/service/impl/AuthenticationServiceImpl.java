package com.SpringSecurity.Spring.Security.service.impl;

import com.SpringSecurity.Spring.Security.dto.JwtAuthenticationResponse;
import com.SpringSecurity.Spring.Security.dto.RefreshTokenRequest;
import com.SpringSecurity.Spring.Security.dto.SignInRequest;
import com.SpringSecurity.Spring.Security.dto.SignUpRequest;
import com.SpringSecurity.Spring.Security.model.User;
import com.SpringSecurity.Spring.Security.repository.UserRepository;
import com.SpringSecurity.Spring.Security.service.AuthenticationService;
import com.SpringSecurity.Spring.Security.service.JWTService;
import com.SpringSecurity.Spring.Security.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;


    @Override
    public User signUp(SignUpRequest signUpRequest){

        User user = new User();

        user.setEmail(signUpRequest.getEmail());
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return userRepository.save(user);

    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest signInRequest){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                signInRequest.getPassword()));


        var user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(()-> new IllegalStateException("Invalid email or password"));

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());

        User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new IllegalStateException("Invalid User Email"));

        if(jwtService.isTokenValid(refreshTokenRequest.getToken(),user)){

            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthenticationResponse;
        }

        return null;
    }
}
