// UserFactory.java
package net.microgoose.mocknet.factory.model;

import net.microgoose.mocknet.auth.model.AuthUser;
import net.microgoose.mocknet.factory.ModelTestFactory;

import java.time.Instant;
import java.util.UUID;

public class UserFactory implements ModelTestFactory<AuthUser> {
    
    @Override
    public AuthUser createNew() {
        return AuthUser.builder()
            .id(UUID.randomUUID())
            .username("newuser")
            .email("new@example.com")
            .passwordHash("newpassword")
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();
    }

    @Override
    public AuthUser createPersisted() {
        return AuthUser.builder()
            .id(UUID.fromString("bb345678-1234-1234-1234-123456789abc"))
            .username("existinguser")
            .email("existing@example.com")
            .passwordHash("existingpassword")
            .createdAt(Instant.now().minusSeconds(7200))
            .updatedAt(Instant.now().minusSeconds(3600))
            .build();
    }

    @Override
    public AuthUser createValid() {
        return AuthUser.builder()
            .id(UUID.randomUUID())
            .username("validuser")
            .email("valid@example.com")
            .passwordHash("validpassword")
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();
    }
}

