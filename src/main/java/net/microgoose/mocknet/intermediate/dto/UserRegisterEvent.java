package net.microgoose.mocknet.intermediate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserRegisterEvent {

    @NotNull
    private UUID id;

    @Email
    private String email;
}
