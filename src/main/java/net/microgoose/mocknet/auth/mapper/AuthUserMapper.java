package net.microgoose.mocknet.auth.mapper;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.auth.dto.AuthRequest;
import net.microgoose.mocknet.auth.model.AuthUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class AuthUserMapper {

    private final PasswordEncoder passwordEncoder;

    public AuthUser fromDto(AuthRequest authRequest) {
        return AuthUser.builder()
            .email(authRequest.getEmail())
            .password(passwordEncoder.encode(authRequest.getPassword()))
            .roles(new HashSet<>())
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();
    }
}
