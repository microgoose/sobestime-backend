package net.sobestime.auth.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.sobestime.auth.config.TokenConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenConfig tokenConfig;
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(tokenConfig.getTokenType())) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authHeader.substring(tokenConfig.getTokenType().length() + 1);

        try {
            JwtAuthenticationToken authentication = (JwtAuthenticationToken) authenticationManager
                .authenticate(new JwtAuthenticationToken(accessToken));

            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException | BadCredentialsException e) {
            // access token битый или истёк — не аутентифицируем
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
