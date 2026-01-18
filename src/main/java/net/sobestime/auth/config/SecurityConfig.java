package net.sobestime.auth.config;

import lombok.RequiredArgsConstructor;
import net.sobestime.auth.security.JwtAuthenticationFilter;
import net.sobestime.auth.security.JwtAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable) // TODO
            .cors(conf -> conf
                .configurationSource(defaultCorsConfiguration())
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(jwtAuthenticationProvider)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authz -> authz
                // TODO make configurable
                // Swagger
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs",
                    "/v3/api-docs/**"
                ).permitAll()

                // Auth Service
                .requestMatchers("/api/v1/auth/register", "/api/v1/auth/login").anonymous()
                .requestMatchers("/api/v1/roles").hasRole("ADMIN")

                // Interview Service
                // ================ Грейды ================
                .requestMatchers(HttpMethod.GET, "/api/v1/grades").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/grades").hasAnyRole("ADMIN")
                
                // ================ Роли интервью ================
                .requestMatchers(HttpMethod.GET, "/api/v1/interview-roles").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/interview-roles").hasAnyRole("ADMIN")
                
                // ================ Скиллы ================
                .requestMatchers(HttpMethod.GET, "/api/v1/skills").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/skills").hasAnyRole("ADMIN")
                
                // ================ Заявки на интервью ================
                .requestMatchers(HttpMethod.GET, "/api/v1/interview-requests/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/interview-requests").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/interview-requests").authenticated()
                
                // ================ Слоты ================
                .requestMatchers(HttpMethod.POST, "/api/v1/interview-slots/*/approve").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/v1/interview-slots/*/reject").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/interview-slots").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/interview-slots").authenticated()

                // ================ Пользовательские данные ================
                .requestMatchers(HttpMethod.GET, "/api/v1/interview-user/requests").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/interview-user/interviews").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/interview-user/slots").authenticated()

                .anyRequest().authenticated()
            )
            .build();
    }

    // TODO
    private UrlBasedCorsConfigurationSource defaultCorsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
