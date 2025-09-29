package net.microgoose.mocknet.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterviewSessionDto {
    UUID id;
    UUID slotBookingId;
    String sessionLink;
    OffsetDateTime createdAt;
}
