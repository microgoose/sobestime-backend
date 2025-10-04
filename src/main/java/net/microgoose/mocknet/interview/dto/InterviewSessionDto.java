package net.microgoose.mocknet.interview.dto;

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
