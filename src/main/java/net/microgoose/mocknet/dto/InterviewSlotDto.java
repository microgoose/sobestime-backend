package net.microgoose.mocknet.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterviewSlotDto {
    UUID id;
    UUID interviewRequestId;
    OffsetDateTime startTime;
    OffsetDateTime endTime;
    boolean isBooked;
}
