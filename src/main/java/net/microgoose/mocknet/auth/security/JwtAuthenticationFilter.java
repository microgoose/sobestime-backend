package net.microgoose.mocknet.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.config.AuthentificationConfig;
import net.microgoose.mocknet.auth.dto.AuthTokensDto;
import net.microgoose.mocknet.auth.service.AuthService;
import net.microgoose.mocknet.auth.service.JwtService;
import net.microgoose.mocknet.auth.service.TokenCookieService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthService authService;
    private final TokenCookieService tokenCookieService;
    private final AuthentificationConfig authConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = extractAccessToken(request);

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (jwtService.isExpired(accessToken)) {
            accessToken = refreshTokens(request, response);
        }

        if (jwtService.isValid(accessToken)) {
            String email = jwtService.getEmailFromToken(accessToken);

            if (email != null) {
                setupAuthentication(request, accessToken, email);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String refreshTokens(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractRefreshToken(request);

        if (refreshToken == null || !jwtService.isValid(refreshToken)) {
            ResponseCookie responseCookie = tokenCookieService.createDeleteRefreshTokenCookie(refreshToken);
            response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

            return null;
        }

        AuthTokensDto tokensDto = authService.login(refreshToken);
        String accessTokenHeader = String.format("%s %s", authConfig.getHeaderPrefix(), tokensDto.getAccessToken());
        String refreshTokenCookie = tokenCookieService
            .createRefreshTokenCookie(tokensDto.getRefreshToken())
            .toString();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie);
        response.addHeader(authConfig.getHeaderName(), accessTokenHeader);

        return tokensDto.getAccessToken();
    }

    private void setupAuthentication(HttpServletRequest request, String accessToken, String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(userDetails, accessToken, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
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
                if (authConfig.getRefreshTokenName().equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
