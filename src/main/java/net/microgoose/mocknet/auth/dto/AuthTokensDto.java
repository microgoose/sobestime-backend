package net.microgoose.mocknet.auth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthTokensDto {
    private String accessToken;
    private String refreshToken;
}
