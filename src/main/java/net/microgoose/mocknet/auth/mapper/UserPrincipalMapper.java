package net.microgoose.mocknet.auth.mapper;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.auth.dto.AuthRequest;
import net.microgoose.mocknet.auth.model.UserPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class UserPrincipalMapper {

    private final PasswordEncoder passwordEncoder;

    public UserPrincipal fromDto(AuthRequest authRequest) {
        return UserPrincipal.builder()
            .email(authRequest.getEmail())
            .password(passwordEncoder.encode(authRequest.getPassword()))
            .roles(new HashSet<>())
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();
    }
}
