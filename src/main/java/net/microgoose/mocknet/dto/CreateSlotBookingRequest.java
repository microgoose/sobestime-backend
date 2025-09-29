package net.microgoose.mocknet.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSlotBookingRequest {
    private UUID slotId;
    private UUID interviewerId;
}
