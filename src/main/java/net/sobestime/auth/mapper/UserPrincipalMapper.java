package net.sobestime.auth.mapper;

import lombok.RequiredArgsConstructor;
import net.sobestime.auth.dto.RegistrationRequest;
import net.sobestime.auth.model.UserPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class UserPrincipalMapper {

    private final PasswordEncoder passwordEncoder;

    public UserPrincipal fromDto(RegistrationRequest request) {
        return UserPrincipal.builder()
            .email(request.getEmail())
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .roles(new HashSet<>())
            .enabled(false)
            .credentialsNonExpired(true)
            .accountNonLocked(true)
            .accountNonExpired(true)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();
    }
}
