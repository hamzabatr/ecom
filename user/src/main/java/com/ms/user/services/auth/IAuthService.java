package com.ms.user.services.auth;

import com.ms.user.configurations.jwt.JwtResponse;
import com.ms.user.controllers.body.CredentialsBody;
import com.ms.user.controllers.body.SignUpBody;
import com.ms.user.exceptions.AlreadyExistException;
import com.ms.user.exceptions.NotFilledException;
import com.ms.user.exceptions.TokenExpiredException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

public interface IAuthService {
    JwtResponse signIn(CredentialsBody credentialsBody)
            throws AuthenticationException, IllegalArgumentException, NotFoundException,
            MissingRequiredPropertiesException, TokenExpiredException;
    void signUp(SignUpBody signUpBody) throws AlreadyExistException, NotFilledException, NotFoundException;
}
