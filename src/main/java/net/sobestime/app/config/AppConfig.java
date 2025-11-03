package net.sobestime.app.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppConfig {

    @Value("${spring.application.name:@null}")
    private String appName;

    @Value("${spring.application.version:@null}")
    private String appVersion;
}
