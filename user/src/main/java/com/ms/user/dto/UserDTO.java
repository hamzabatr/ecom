package com.ms.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private List<String> roles;
    private boolean isActivated;
}
