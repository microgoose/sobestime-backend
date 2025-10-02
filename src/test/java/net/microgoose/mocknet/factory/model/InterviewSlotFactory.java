// InterviewSlotFactory.java
package net.microgoose.mocknet.factory.model;

import net.microgoose.mocknet.factory.ModelTestFactory;
import net.microgoose.mocknet.model.InterviewSlot;

import java.time.OffsetDateTime;
import java.util.UUID;

public class InterviewSlotFactory implements ModelTestFactory<InterviewSlot> {
    
    private final InterviewRequestFactory interviewRequestFactory = new InterviewRequestFactory();

    @Override
    public InterviewSlot createNew() {
        OffsetDateTime now = OffsetDateTime.now();
        return InterviewSlot.builder()
            .id(UUID.randomUUID())
            .interviewRequest(interviewRequestFactory.createNew())
            .startTime(now.plusHours(1).toInstant())
            .endTime(now.plusHours(2).toInstant())
            .isBooked(false)
            .build();
    }

    @Override
    public InterviewSlot createPersisted() {
        OffsetDateTime now = OffsetDateTime.now();
        return InterviewSlot.builder()
            .id(UUID.fromString("55345678-1234-1234-1234-123456789abc"))
            .interviewRequest(interviewRequestFactory.createPersisted())
            .startTime(now.plusHours(1).toInstant())
            .endTime(now.plusHours(2).toInstant())
            .isBooked(true)
            .build();
    }

    @Override
    public InterviewSlot createValid() {
        OffsetDateTime now = OffsetDateTime.now();
        return InterviewSlot.builder()
            .id(UUID.randomUUID())
            .interviewRequest(interviewRequestFactory.createValid())
            .startTime(now.plusHours(1).toInstant())
            .endTime(now.plusHours(2).toInstant())
            .isBooked(false)
            .build();
    }
}