package com.ms.user.configurations.jwt;

import com.ms.user.entities.Token;
import com.ms.user.entities.User;
import com.ms.user.repositories.ITokenRepository;
import com.ms.user.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {
    @Value("${jwt.jwt-expiration-in-minutes}")
    private Long tokenExpiration;
    @Value("${jwt.jwt-refresh-expiration-in-hours}")
    private Long tokenRefreshExpiration;


    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final IUserRepository userRepository;
    private final ITokenRepository tokenRepository;

    public JwtResponse generateTokens(List<String> roles, String username, boolean stayConnected)
            throws IllegalArgumentException {
        User user = userRepository.findUserByUsername(username).orElse(new User());

        return JwtResponse.builder()
                .accessToken(generateAccessToken(roles, user))
                .refreshToken(stayConnected ? generateRefreshToken(user) : null)
                .build();
    }

    public String extractUsernameFromToken(String token) {
        return jwtDecoder.decode(token).getSubject();
    }

    public boolean isTokenValid(UserDetails userDetails, String token) {
        try {
            final String username = extractUsernameFromToken(token);
            return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }

    public boolean isTokenValid(User user, String token) {
        try {
            final String username = extractUsernameFromToken(token);
            return (username.equals(user.getUsername())) && !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }


    private Instant extractExpirationFromToken(String token) {
        return jwtDecoder.decode(token).getExpiresAt();
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationFromToken(token).isBefore(Instant.now());
    }

    private void deleteUserAccessTokensIfExists(User user) {
        if (!CollectionUtils.isEmpty(user.getTokens())) {
            tokenRepository.deleteAllById(
                    user.getTokens().stream()
                            .filter(t -> t.getType().equals(EToken.ACCESS_TOKEN))
                            .map(Token::getId)
                            .toList()
            );
        }
    }

    private String generateAccessToken(List<String> roles, User user) {
        String accessToken = buildToken(
                user,
                tokenExpiration,
                Map.of(
                        "email", user.getEmail(),
                        "roles", roles,
                        "isActivated", user.isActivated(),
                        "generatedTokenId", user.getUsername() + Math.random() * 2
                )
        );
        tokenRepository.save(
                Token.builder()
                        .token(accessToken)
                        .user(user)
                        .type(EToken.ACCESS_TOKEN)
                        .build()
        );

        return accessToken;
    }

    private String generateRefreshToken(User user) {
        String refreshToken = buildToken(user, tokenRefreshExpiration, new HashMap<>());
        tokenRepository.save(
                Token.builder()
                        .token(refreshToken)
                        .user(user)
                        .type(EToken.REFRESH_TOKEN)
                        .build()
        );

        return refreshToken;
    }

    private String buildToken(User user, long expiration, Map<String, ?> claims) {
        JwtEncoderParameters jwtEncoderParameters =
                JwtEncoderParameters.from(
                        JwsHeader
                                .with(MacAlgorithm.HS512)
                                .build(),
                        JwtClaimsSet
                                .builder()
                                .issuedAt(Instant.now())
                                .expiresAt(Instant.now().plus(expiration, ChronoUnit.SECONDS))
                                .subject(user.getUsername())
                                .id(user.getId())
                                .claims(stringObjectMap -> stringObjectMap.putAll(claims))
//                                .claim("email", user.getEmail())
//                                .claim("roles", roles)
//                                .claim("isActivated", user.isActivated())
//                                .claim("generatedTokenId", user.getUsername() + Math.random()*2)
                                .build()
                );
        return jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    }

}
