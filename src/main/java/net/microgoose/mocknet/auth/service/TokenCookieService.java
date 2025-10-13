package net.microgoose.mocknet.auth.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.auth.config.TokenConfig;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenCookieService {

    private final TokenConfig tokenConfig;

    public ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(tokenConfig.getRefreshTokenName(), refreshToken)
            .httpOnly(true)
            .secure(tokenConfig.isSecure())
            .path(tokenConfig.getRefreshTokenPath())
            .maxAge(tokenConfig.getRefreshTokenExpiration().getSeconds())
            .sameSite(tokenConfig.getSameSite())
            .build();
    }

    public ResponseCookie createDeleteRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(tokenConfig.getRefreshTokenName(), refreshToken)
            .httpOnly(true)
            .secure(tokenConfig.isSecure())
            .path(tokenConfig.getRefreshTokenPath())
            .maxAge(tokenConfig.getRefreshTokenExpiration().getSeconds())
            .sameSite(tokenConfig.getSameSite())
            .build();
    }

}
