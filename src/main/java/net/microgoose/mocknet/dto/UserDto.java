package net.microgoose.mocknet.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
