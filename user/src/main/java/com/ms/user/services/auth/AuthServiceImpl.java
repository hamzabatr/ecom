package com.ms.user.services.auth;

import com.ms.user.configurations.jwt.EToken;
import com.ms.user.configurations.jwt.JwtResponse;
import com.ms.user.configurations.jwt.JwtUtils;
import com.ms.user.controllers.body.CredentialsBody;
import com.ms.user.controllers.body.SignUpBody;
import com.ms.user.controllers.body.enums.EGrantType;
import com.ms.user.entities.Enum.ERole;
import com.ms.user.entities.Token;
import com.ms.user.entities.User;
import com.ms.user.exceptions.AlreadyExistException;
import com.ms.user.exceptions.NotFilledException;
import com.ms.user.exceptions.TokenExpiredException;
import com.ms.user.repositories.IRoleRepository;
import com.ms.user.repositories.ITokenRepository;
import com.ms.user.repositories.IUserRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private PasswordEncoder passwordEncoder;
    private IUserRepository userRepository;
    private UserDetailsService userDetailsService;
    private IRoleRepository roleRepository;
    private ITokenRepository tokenRepository;
    private JwtDecoder jwtDecoder;

    @Override
    @Transactional
    public JwtResponse signIn(CredentialsBody credentialsBody) throws
            AuthenticationException, IllegalArgumentException, NotFoundException, MissingRequiredPropertiesException,
            TokenExpiredException {

        boolean checkGrantTypeNoneExistence = Arrays.stream(EGrantType.values())
                .noneMatch(gt -> gt.label.equals(credentialsBody.getGrantType()));
        if (checkGrantTypeNoneExistence) {
            throw new NotFoundException();
        }

        if (credentialsBody.getGrantType().equals(EGrantType.PASSWORD.label)) {
            return authenticateUserWithPasswordThenGenerateToken(credentialsBody);
        }

        if (credentialsBody.getGrantType().equals(EGrantType.REFRESH_TOKEN.label)) {
            return authenticateUserWithRefreshTokenThenGenerateToken(credentialsBody);
        }

        return null;
    }

    @Override
    @Transactional
    public void signUp(SignUpBody signUpBody) throws AlreadyExistException, NotFilledException, NotFoundException {
        if (!StringUtils.hasText(signUpBody.getUsername()) ||
                !StringUtils.hasText(signUpBody.getEmail()) ||
                !StringUtils.hasText(signUpBody.getPassword()) ||
                CollectionUtils.isEmpty(signUpBody.getRoleSet())) {
            throw new NotFilledException();
        }

        if (userRepository.existsByUsernameOrEmail(signUpBody.getUsername(), signUpBody.getEmail())) {
            throw new AlreadyExistException();
        }

        User user = new User(
                signUpBody.getUsername(),
                signUpBody.getEmail(),
                passwordEncoder.encode(signUpBody.getPassword()),
                true);

        user.setRoles(
                signUpBody.getRoleSet().stream()
                        .map(r ->
                                roleRepository.findRoleByName(ERole.valueOf(r))
                                        .orElseThrow(NotFoundException::new))
                        .collect(Collectors.toSet()));

        userRepository.save(user);

    }

    private JwtResponse authenticateUserWithPasswordThenGenerateToken(CredentialsBody credentialsBody) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentialsBody.getUsername(), credentialsBody.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUsername(credentialsBody.getUsername());

        List<Token> userAccessTokens = user.getTokens().stream()
                .filter(t -> t.getType().equals(EToken.ACCESS_TOKEN) && jwtUtils.isTokenValid(user, t.getToken()))
                .toList();

        if (userAccessTokens.size() != 0) {
            return JwtResponse.builder()
                    .accessToken(userAccessTokens.get(0).getToken())
                    .refreshToken(
                            credentialsBody.isStayConnected() ?
                                    Objects.requireNonNull(
                                            user.getTokens().stream()
                                                    .filter(t -> t.getType().equals(EToken.REFRESH_TOKEN))
                                                    .findFirst()
                                                    .orElse(null)
                                    ).getToken() :
                                    null
                    )
                    .build();
        }


        tokenRepository.deleteAll(
                user.getTokens().stream()
                        .filter(t -> t.getType().equals(EToken.ACCESS_TOKEN))
                        .toList()
        );

        return jwtUtils.generateTokens(
                authentication.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .toList(),
                credentialsBody.getUsername(),
                credentialsBody.isStayConnected());
    }

    private JwtResponse authenticateUserWithRefreshTokenThenGenerateToken(CredentialsBody credentialsBody)
            throws MissingRequiredPropertiesException, TokenExpiredException {
        String refreshToken = credentialsBody.getRefreshToken();

        if (!StringUtils.hasText(refreshToken)) {
            throw new MissingRequiredPropertiesException();
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(
                jwtDecoder.decode(refreshToken).getSubject()
        );

        tokenRepository.deleteTokenByUserAndType(userRepository.findByUsername(userDetails.getUsername()), EToken.ACCESS_TOKEN);

        if (!jwtUtils.isTokenValid(userDetails, refreshToken) ||
                !tokenRepository.existsByTokenAndType(refreshToken, EToken.REFRESH_TOKEN)) {
            tokenRepository.deleteTokenByTokenAndType(refreshToken, EToken.REFRESH_TOKEN);
            throw new TokenExpiredException();
        }

        return jwtUtils.generateTokens(
                userDetails.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .toList(),
                credentialsBody.getUsername(),
                false);
    }
}