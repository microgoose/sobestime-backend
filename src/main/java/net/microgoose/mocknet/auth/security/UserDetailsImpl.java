package net.microgoose.mocknet.auth.security;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.auth.model.AuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final AuthUser authUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authUser.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return authUser.getPassword();
    }

    @Override
    public String getUsername() {
        return authUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return authUser.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return authUser.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return authUser.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return authUser.isEnabled();
    }

}
