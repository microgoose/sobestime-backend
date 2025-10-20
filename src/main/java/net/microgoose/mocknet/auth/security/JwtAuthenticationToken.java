package net.microgoose.mocknet.auth.security;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String accessToken;
    private final String refreshToken;

    public JwtAuthenticationToken(String accessToken, String refreshToken) {
        super(null, null);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public JwtAuthenticationToken(UserDetails principal, String accessToken,
                                  String refreshToken, Collection<? extends GrantedAuthority> authorities) {
        super(principal, null, authorities);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
