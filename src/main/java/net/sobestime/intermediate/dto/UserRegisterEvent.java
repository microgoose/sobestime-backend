package net.sobestime.intermediate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserRegisterEvent {

    @NotNull
    private UUID id;

    @NotBlank
    private String username;
}
