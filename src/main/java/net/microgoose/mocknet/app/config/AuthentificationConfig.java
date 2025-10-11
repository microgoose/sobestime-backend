package net.microgoose.mocknet.app.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AuthentificationConfig {

    @Value("${app.auth.header-name}")
    private String headerName;

    @Value("${app.auth.header-prefix}")
    private String headerPrefix;

    @Value("${app.auth.access-token-name}")
    private String accessTokenName;

    @Value("${app.auth.refresh-token-name}")
    private String refreshTokenName;

}

