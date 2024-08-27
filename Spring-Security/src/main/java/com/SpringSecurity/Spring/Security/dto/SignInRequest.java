package com.SpringSecurity.Spring.Security.dto;

import lombok.Data;

@Data
public class SignInRequest {

    private String email;

    private String password;
}
