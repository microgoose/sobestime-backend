package net.sobestime.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.sobestime.app.config.AuthentificationConfig;
import net.sobestime.auth.config.TokenConfig;
import net.sobestime.auth.service.TokenCookieService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenCookieService tokenCookieService;
    private final AuthentificationConfig authConfig;
    private final TokenConfig tokenConfig;
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().contains("/login") ||
            request.getRequestURI().contains("/register")) {

            filterChain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = extractAccessToken(request);
        String refreshToken = extractRefreshToken(request);

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        JwtAuthenticationToken authentication = (JwtAuthenticationToken)
            authenticationManager.authenticate(new JwtAuthenticationToken(accessToken, refreshToken));

        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);

            response.addHeader(tokenConfig.getAccessTokenResponseHeader(), authentication.getAccessToken());
            response.addHeader(HttpHeaders.SET_COOKIE, tokenCookieService
                .createRefreshTokenCookie(authentication.getRefreshToken())
                .toString());
        }

        filterChain.doFilter(request, response);
    }

    private String extractAccessToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(authConfig.getHeaderPrefix())) {
            return authHeader.substring(authConfig.getHeaderPrefix().length());
        }

        return null;
    }

    private String extractRefreshToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (tokenConfig.getRefreshTokenName().equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
