package net.sobestime.auth.security;

import lombok.RequiredArgsConstructor;
import net.sobestime.auth.service.AuthService;
import net.sobestime.auth.service.JwtService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import static net.sobestime.auth.config.MessageDictionary.AUTH_FAILED;
import static net.sobestime.auth.config.MessageDictionary.INCORRECT_JWT_DATA;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;
    private final AuthService authService;
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authToken = (JwtAuthenticationToken) authentication;
        String accessToken = authToken.getAccessToken();

        if (!jwtService.isValid(accessToken))
            throw new BadCredentialsException(INCORRECT_JWT_DATA);

        String email = jwtService.getEmailFromToken(accessToken);
        if (email == null || email.isEmpty())
            throw new BadCredentialsException(INCORRECT_JWT_DATA);

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            return new JwtAuthenticationToken(userDetails, accessToken, userDetails.getAuthorities());
        } catch (Exception e) {
            throw new BadCredentialsException(AUTH_FAILED, e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
