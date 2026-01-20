package net.sobestime.auth.security;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String accessToken;

    public JwtAuthenticationToken(String accessToken) {
        super(null, null);
        this.accessToken = accessToken;
    }

    public JwtAuthenticationToken(UserDetails principal, String accessToken,
                                  Collection<? extends GrantedAuthority> authorities) {
        super(principal, null, authorities);
        this.accessToken = accessToken;
    }
}
