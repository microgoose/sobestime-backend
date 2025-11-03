package net.sobestime.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static net.sobestime.auth.config.MessageDictionary.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {

    @Email(message = EMAIL_NOT_SPECIFIED)
    private String email;

    @NotBlank(message = USERNAME_NOT_SPECIFIED)
    private String username;

    @NotBlank(message = PASSWORD_NOT_SPECIFIED)
    private String password;
}
