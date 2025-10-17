package net.microgoose.mocknet.auth.mapper;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.auth.dto.RegistrationRequest;
import net.microgoose.mocknet.auth.model.UserPrincipal;
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
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();
    }
}
