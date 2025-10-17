package net.microgoose.mocknet.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static net.microgoose.mocknet.auth.config.MessageDictionary.EMAIL_NOT_SPECIFIED;
import static net.microgoose.mocknet.auth.config.MessageDictionary.PASSWORD_NOT_SPECIFIED;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    @Email(message = EMAIL_NOT_SPECIFIED)
    private String email;

    @NotBlank(message = PASSWORD_NOT_SPECIFIED)
    private String password;
}
