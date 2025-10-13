package net.microgoose.mocknet.interview.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import net.microgoose.mocknet.interview.config.ErrorDictionary;

import java.util.UUID;

@Data
@Builder
public class CreateInterviewUserRequest {

    @NotNull(message = ErrorDictionary.USER_ID_NOT_SPECIFIED)
    private UUID uuid;

    @NotBlank(message = ErrorDictionary.USERNAME_NOT_SPECIFIED)
    private String username;

    @NotBlank(message = ErrorDictionary.AVATAR_URL_NOT_SPECIFIED)
    private String avatarUrl;
}