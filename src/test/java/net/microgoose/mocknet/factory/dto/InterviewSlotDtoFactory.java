package net.microgoose.mocknet.factory.dto;

import net.microgoose.mocknet.dto.InterviewSlotDto;
import net.microgoose.mocknet.factory.DataTestFactory;

import java.time.OffsetDateTime;
import java.util.UUID;

public class InterviewSlotDtoFactory implements DataTestFactory<InterviewSlotDto> {

    @Override
    public InterviewSlotDto createNew() {
        OffsetDateTime now = OffsetDateTime.now();
        return InterviewSlotDto.builder()
            .id(UUID.randomUUID())
            .interviewRequestId(UUID.randomUUID())
            .startTime(now.plusHours(1))
            .endTime(now.plusHours(2))
            .isBooked(false)
            .build();
    }

    @Override
    public InterviewSlotDto createValid() {
        OffsetDateTime now = OffsetDateTime.now();
        return InterviewSlotDto.builder()
            .id(UUID.randomUUID())
            .interviewRequestId(UUID.randomUUID())
            .startTime(now.plusHours(1))
            .endTime(now.plusHours(2))
            .isBooked(false)
            .build();
    }
}
