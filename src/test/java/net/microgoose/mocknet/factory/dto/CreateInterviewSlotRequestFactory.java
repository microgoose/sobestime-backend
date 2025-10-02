package net.microgoose.mocknet.factory.dto;

import net.microgoose.mocknet.dto.CreateInterviewSlotRequest;
import net.microgoose.mocknet.factory.DataTestFactory;

import java.time.OffsetDateTime;
import java.util.UUID;

public class CreateInterviewSlotRequestFactory implements DataTestFactory<CreateInterviewSlotRequest> {

    @Override
    public CreateInterviewSlotRequest createNew() {
        OffsetDateTime now = OffsetDateTime.now();
        return CreateInterviewSlotRequest.builder()
            .interviewRequestId(UUID.randomUUID())
            .startTime(now.plusHours(1))
            .endTime(now.plusHours(2))
            .build();
    }

    @Override
    public CreateInterviewSlotRequest createValid() {
        OffsetDateTime now = OffsetDateTime.now();
        return CreateInterviewSlotRequest.builder()
            .interviewRequestId(UUID.randomUUID())
            .startTime(now.plusHours(1))
            .endTime(now.plusHours(2))
            .build();
    }
}
