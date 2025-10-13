package net.microgoose.mocknet.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRoleRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
