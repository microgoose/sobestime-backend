package net.microgoose.mocknet.factory.dto;

import net.microgoose.mocknet.dto.InterviewSessionDto;
import net.microgoose.mocknet.factory.DataTestFactory;

import java.time.OffsetDateTime;
import java.util.UUID;

public class InterviewSessionDtoFactory implements DataTestFactory<InterviewSessionDto> {

    @Override
    public InterviewSessionDto createNew() {
        return InterviewSessionDto.builder()
            .id(UUID.randomUUID())
            .slotBookingId(UUID.randomUUID())
            .sessionLink("new-dto-link")
            .createdAt(OffsetDateTime.now())
            .build();
    }

    @Override
    public InterviewSessionDto createValid() {
        return InterviewSessionDto.builder()
            .id(UUID.randomUUID())
            .slotBookingId(UUID.randomUUID())
            .sessionLink("valid-dto-link")
            .createdAt(OffsetDateTime.now())
            .build();
    }
}
