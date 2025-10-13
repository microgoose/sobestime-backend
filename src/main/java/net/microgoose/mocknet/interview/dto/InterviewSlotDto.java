package net.microgoose.mocknet.interview.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class InterviewSlotDto {
    private UUID uuid;
    private OffsetDateTime startTime;
    private String status;
    private List<InterviewUserDto> bookers;
}
