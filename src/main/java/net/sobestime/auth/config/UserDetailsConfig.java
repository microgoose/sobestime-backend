package net.sobestime.auth.config;

import lombok.RequiredArgsConstructor;
import net.sobestime.auth.service.UserPrincipalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class UserDetailsConfig {

    private final UserPrincipalService userPrincipalService;

    @Bean
    public UserDetailsService userDetailsService() {
        return userPrincipalService;
    }

}
