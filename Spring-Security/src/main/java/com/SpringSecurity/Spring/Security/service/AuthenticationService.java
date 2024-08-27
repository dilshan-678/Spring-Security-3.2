package com.SpringSecurity.Spring.Security.service;

import com.SpringSecurity.Spring.Security.dto.JwtAuthenticationResponse;
import com.SpringSecurity.Spring.Security.dto.RefreshTokenRequest;
import com.SpringSecurity.Spring.Security.dto.SignInRequest;
import com.SpringSecurity.Spring.Security.dto.SignUpRequest;
import com.SpringSecurity.Spring.Security.model.User;

public interface AuthenticationService {

    User signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
