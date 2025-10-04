package net.microgoose.mocknet.app.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private int code;
    private OffsetDateTime timestamp;
    private String message;
}
