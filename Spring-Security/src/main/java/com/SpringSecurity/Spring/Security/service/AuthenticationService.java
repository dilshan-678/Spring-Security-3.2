package com.SpringSecurity.Spring.Security.service;

import com.SpringSecurity.Spring.Security.dto.SignUpRequest;
import com.SpringSecurity.Spring.Security.model.User;

public interface AuthenticationService {

    User signUp(SignUpRequest signUpRequest);
}
