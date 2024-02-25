package com.ms.user.controllers;

import com.ms.user.configurations.jwt.JwtResponse;
import com.ms.user.controllers.body.CredentialsBody;
import com.ms.user.controllers.body.SignUpBody;
import com.ms.user.exceptions.AlreadyExistException;
import com.ms.user.exceptions.NotFilledException;
import com.ms.user.exceptions.TokenExpiredException;
import com.ms.user.services.auth.IAuthService;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping(path = "/sign-in")
    public ResponseEntity<JwtResponse> signIn(@Valid @RequestBody CredentialsBody credentialsBody) {
        try {
            return ResponseEntity.ok().body(authService.signIn(credentialsBody));
        } catch (AuthenticationException | TokenExpiredException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//                    .body(Map.of("error", "Username or password not correct."));
        }
//        catch (NotFoundException e) {
//            return ResponseEntity.internalServerError().body(Map.of("error", "grantType " + (StringUtils.hasText(credentialsBody.getGrantType()) ? "not found." : "required.")));
//        } catch (MissingRequiredPropertiesException e) {
//            return ResponseEntity.internalServerError().body(Map.of("error", "refresh-token required."));
//        } catch (TokenExpiredException e) {
//            return ResponseEntity.internalServerError().body(Map.of("error", "refresh-token expired."));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.internalServerError().body(Map.of("error", "Internal Error."));
//        }
    }

    @PostMapping(path = "/sign-up")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpBody signUpBody) {
        try {
            authService.signUp(signUpBody);
            return ResponseEntity.ok().body("User created");
        } catch (AlreadyExistException e) {
            return ResponseEntity.badRequest().body("Username or email already exist.");
        } catch (NotFilledException ex) {
            return ResponseEntity.badRequest().body("Should fill all.");
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body("Role not found.");
        }
    }

}
