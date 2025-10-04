package net.microgoose.mocknet.interview.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SlotBookingDto {
    UUID id;
    UUID slotId;
    UUID interviewerId;
    OffsetDateTime bookedAt;
}
