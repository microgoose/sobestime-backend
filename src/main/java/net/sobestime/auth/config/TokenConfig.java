package net.sobestime.auth.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Getter
public class TokenConfig {

    @Value("${app.auth.jwt-secret}")
    private String jwtSecret;

    @Value("${app.auth.access-token-expiration}")
    private Duration accessTokenExpiration;

    @Value("${app.auth.access-token-name}")
    private String accessTokenName;

    @Value("${app.auth.access-token-response-header}")
    private String accessTokenResponseHeader;

    @Value("${app.auth.refresh-token-name}")
    private String refreshTokenName;

    @Value("${app.auth.refresh-token-expiration}")
    private Duration refreshTokenExpiration;

    @Value("${app.auth.refresh-token-path}")
    private String refreshTokenPath;

    @Value("${app.auth.secure}")
    private boolean secure;

    @Value("${app.auth.same-site}")
    private String sameSite;

}
