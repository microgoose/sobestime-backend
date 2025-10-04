package net.microgoose.mocknet.user.mapper;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.config.DateTimeService;
import net.microgoose.mocknet.user.dto.CreateUserRequest;
import net.microgoose.mocknet.user.dto.UserDto;
import net.microgoose.mocknet.user.model.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final DateTimeService dateTimeService;

    public User fromDto(CreateUserRequest request) {
        return User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .passwordHash(null)
            .updatedAt(Instant.now())
            .createdAt(Instant.now())
            .build();
    }

    public UserDto toDto(User request) {
        return UserDto.builder()
            .id(request.getId())
            .username(request.getUsername())
            .email(request.getEmail())
            .createdAt(dateTimeService.toOffsetDateTime(request.getCreatedAt()))
            .updatedAt(dateTimeService.toOffsetDateTime(request.getUpdatedAt()))
            .build();
    }

    public List<UserDto> toDto(List<User> request) {
        return request.stream().map(this::toDto).toList();
    }
}
