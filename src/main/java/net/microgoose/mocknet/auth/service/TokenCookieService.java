package net.microgoose.mocknet.auth.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.config.AuthentificationConfig;
import net.microgoose.mocknet.auth.config.TokenConfig;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenCookieService {

    private final TokenConfig tokenConfig;
    private final AuthentificationConfig authConfig;

    public ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(authConfig.getRefreshTokenName(), refreshToken)
            .httpOnly(true)
            .secure(tokenConfig.isSecure())
            .path(tokenConfig.getRefreshTokenPath())
            .maxAge(tokenConfig.getRefreshTokenExpiration().getSeconds())
            .sameSite(tokenConfig.getSameSite())
            .build();
    }

    public ResponseCookie createDeleteRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(authConfig.getRefreshTokenName(), refreshToken)
            .httpOnly(true)
            .secure(tokenConfig.isSecure())
            .path(tokenConfig.getRefreshTokenPath())
            .maxAge(tokenConfig.getRefreshTokenExpiration().getSeconds())
            .sameSite(tokenConfig.getSameSite())
            .build();
    }

}
