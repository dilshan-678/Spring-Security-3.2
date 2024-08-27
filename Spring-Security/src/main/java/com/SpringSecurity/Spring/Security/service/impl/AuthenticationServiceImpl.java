package com.SpringSecurity.Spring.Security.service.impl;

import com.SpringSecurity.Spring.Security.dto.SignUpRequest;
import com.SpringSecurity.Spring.Security.model.User;
import com.SpringSecurity.Spring.Security.repository.UserRepository;
import com.SpringSecurity.Spring.Security.service.AuthenticationService;
import com.SpringSecurity.Spring.Security.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


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
}
