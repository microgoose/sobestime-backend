package net.microgoose.mocknet.auth.config;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.auth.service.UserPrincipalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class UserDetailsConfig {

    private final UserPrincipalService userPrincipalService;

    @Bean
    public UserDetailsService userDetailsService() {
        return userPrincipalService;
    }

}
