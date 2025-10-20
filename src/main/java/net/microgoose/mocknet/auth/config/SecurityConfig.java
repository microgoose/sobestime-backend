package net.microgoose.mocknet.auth.config;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.auth.security.JwtAuthenticationFilter;
import net.microgoose.mocknet.auth.security.JwtAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable) // TODO
            .cors(AbstractHttpConfigurer::disable) // TODO
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(jwtAuthenticationProvider)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authz -> authz
                // TODO make configurable
                // Auth Service
                .requestMatchers("/api/v1/auth/register", "/api/v1/auth/login").anonymous()
                .requestMatchers("/api/v1/roles").hasRole("ADMIN")

                // Interview Service
                .requestMatchers(HttpMethod.POST, "/api/v1/grades").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/interview-roles").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/skills").hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            .build();
    }

}
