// UserFactory.java
package net.microgoose.mocknet.factory.model;

import net.microgoose.mocknet.factory.ModelTestFactory;
import net.microgoose.mocknet.user.model.User;

import java.time.Instant;
import java.util.UUID;

public class UserFactory implements ModelTestFactory<User> {
    
    @Override
    public User createNew() {
        return User.builder()
            .id(UUID.randomUUID())
            .username("newuser")
            .email("new@example.com")
            .passwordHash("newpassword")
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();
    }

    @Override
    public User createPersisted() {
        return User.builder()
            .id(UUID.fromString("bb345678-1234-1234-1234-123456789abc"))
            .username("existinguser")
            .email("existing@example.com")
            .passwordHash("existingpassword")
            .createdAt(Instant.now().minusSeconds(7200))
            .updatedAt(Instant.now().minusSeconds(3600))
            .build();
    }

    @Override
    public User createValid() {
        return User.builder()
            .id(UUID.randomUUID())
            .username("validuser")
            .email("valid@example.com")
            .passwordHash("validpassword")
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();
    }
}

