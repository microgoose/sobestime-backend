package net.microgoose.mocknet.interview.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import net.microgoose.mocknet.interview.config.MessageDictionary;

import java.util.UUID;

@Data
@Builder
public class CreateInterviewUserRequest {

    @NotNull(message = MessageDictionary.USER_ID_NOT_SPECIFIED)
    private UUID uuid;

    @NotBlank(message = MessageDictionary.USERNAME_NOT_SPECIFIED)
    private String username;

    @NotBlank(message = MessageDictionary.AVATAR_URL_NOT_SPECIFIED)
    private String avatarUrl;
}