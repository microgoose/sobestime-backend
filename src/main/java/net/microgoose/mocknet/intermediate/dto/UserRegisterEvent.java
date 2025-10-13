package net.microgoose.mocknet.intermediate.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserRegisterEvent {
    private UUID id;
    private String email;
}
