package net.microgoose.mocknet.factory.dto;

import net.microgoose.mocknet.factory.DataTestFactory;
import net.microgoose.mocknet.user.dto.UserDto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class UserDtoFactory implements DataTestFactory<UserDto> {

    @Override
    public UserDto createNew() {
        return UserDto.builder()
            .id(UUID.randomUUID())
            .username("newdto")
            .email("newdto@example.com")
            .createdAt(OffsetDateTime.now())
            .updatedAt(OffsetDateTime.now())
            .build();
    }

    @Override
    public UserDto createValid() {
        return UserDto.builder()
            .id(UUID.randomUUID())
            .username("validdto")
            .email("validdto@example.com")
            .createdAt(OffsetDateTime.now())
            .updatedAt(OffsetDateTime.now())
            .build();
    }
}
