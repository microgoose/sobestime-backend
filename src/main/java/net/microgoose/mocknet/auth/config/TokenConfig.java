package net.microgoose.mocknet.auth.config;

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

    @Value("${app.auth.refresh-token-expiration}")
    private Duration refreshTokenExpiration;

    @Value("${app.auth.refresh-token-path}")
    private String refreshTokenPath;

    @Value("${app.auth.secure}")
    private boolean secure;

    @Value("${app.auth.same-site}")
    private String sameSite;

}
