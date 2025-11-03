package net.sobestime.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static net.sobestime.auth.config.MessageDictionary.ROLE_DESCRIPTION_NOT_SPECIFIED;
import static net.sobestime.auth.config.MessageDictionary.ROLE_NAME_NOT_SPECIFIED;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRoleRequest {

    @NotBlank(message = ROLE_NAME_NOT_SPECIFIED)
    private String name;

    @NotBlank(message = ROLE_DESCRIPTION_NOT_SPECIFIED)
    private String description;
}
