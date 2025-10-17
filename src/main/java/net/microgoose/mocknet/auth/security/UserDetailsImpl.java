package net.microgoose.mocknet.auth.security;

import net.microgoose.mocknet.auth.model.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public record UserDetailsImpl(UserPrincipal userPrincipal) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userPrincipal.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
    }

    public UUID getId() {
        return userPrincipal.getId();
    }

    @Override
    public String getPassword() {
        return userPrincipal.getPassword();
    }

    @Override
    public String getUsername() {
        return userPrincipal.getUsername();
    }

    public String getEmail() {
        return userPrincipal.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userPrincipal.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userPrincipal.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userPrincipal.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return userPrincipal.isEnabled();
    }

}
