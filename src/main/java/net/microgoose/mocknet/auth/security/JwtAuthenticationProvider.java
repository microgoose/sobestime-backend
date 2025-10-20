package net.microgoose.mocknet.auth.security;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.auth.dto.AuthTokensDto;
import net.microgoose.mocknet.auth.service.AuthService;
import net.microgoose.mocknet.auth.service.JwtService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import static net.microgoose.mocknet.auth.config.MessageDictionary.AUTH_FAILED;
import static net.microgoose.mocknet.auth.config.MessageDictionary.INCORRECT_JWT_DATA;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;
    private final AuthService authService;
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            JwtAuthenticationToken authToken = (JwtAuthenticationToken) authentication;
            String accessToken = authToken.getAccessToken();
            String refreshToken = authToken.getRefreshToken();

            if (jwtService.isExpired(accessToken)) {
                AuthTokensDto authTokensDto = authService.login(refreshToken);
                accessToken = authTokensDto.getAccessToken();
                refreshToken = authTokensDto.getRefreshToken();
            }

            if (!jwtService.isValid(accessToken)) {
                String email = jwtService.getEmailFromToken(accessToken);

                if (email != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    return new JwtAuthenticationToken(
                        userDetails, accessToken, refreshToken, userDetails.getAuthorities());
                }
            }

            throw new BadCredentialsException(INCORRECT_JWT_DATA);
        } catch (Exception e) {
            throw new BadCredentialsException(AUTH_FAILED, e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
