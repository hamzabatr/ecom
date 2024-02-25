package com.ms.user.controllers.body;

import com.ms.user.controllers.body.enums.EGrantType;
import lombok.Getter;

@Getter
public class CredentialsBody {
    private String username;
    private String grantType;
    private String password;
    private String refreshToken;
    private boolean stayConnected;
}
