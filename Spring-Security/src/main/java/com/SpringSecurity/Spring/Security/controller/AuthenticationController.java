package com.SpringSecurity.Spring.Security.controller;

import com.SpringSecurity.Spring.Security.dto.JwtAuthenticationResponse;
import com.SpringSecurity.Spring.Security.dto.RefreshTokenRequest;
import com.SpringSecurity.Spring.Security.dto.SignInRequest;
import com.SpringSecurity.Spring.Security.dto.SignUpRequest;
import com.SpringSecurity.Spring.Security.model.User;
import com.SpringSecurity.Spring.Security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest){

        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest){

        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }


    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){

        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

}
