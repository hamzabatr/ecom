package com.ms.user.controllers.body;

import lombok.Getter;

import java.util.Set;

@Getter
public class SignUpBody {
    private String username;
    private String password;
    private String email;
    private Set<String> roleSet;
}
