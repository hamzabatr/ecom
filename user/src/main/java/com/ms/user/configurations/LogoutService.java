package com.ms.user.configurations;

import com.ms.user.configurations.jwt.EToken;
import com.ms.user.entities.Token;
import com.ms.user.repositories.ITokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final static String BEARER = "Bearer ";

    private final ITokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authHeader == null ||!authHeader.startsWith(BEARER)) {
            return;
        }

        final String jwt = authHeader.replace(BEARER, "");
        Token storedToken = tokenRepository.findTokenByTokenAndType(jwt, EToken.ACCESS_TOKEN).orElse(null);

        if (storedToken != null) {
            tokenRepository.delete(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
