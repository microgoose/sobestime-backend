// InterviewSessionFactory.java
package net.microgoose.mocknet.factory.model;

import net.microgoose.mocknet.factory.ModelTestFactory;
import net.microgoose.mocknet.interview.model.InterviewSession;

import java.time.Instant;
import java.util.UUID;

public class InterviewSessionFactory implements ModelTestFactory<InterviewSession> {
    
    private final SlotBookingFactory slotBookingFactory = new SlotBookingFactory();

    @Override
    public InterviewSession createNew() {
        return InterviewSession.builder()
            .id(UUID.randomUUID())
            .slotBooking(slotBookingFactory.createNew())
            .sessionLink("new-link")
            .createdAt(Instant.now())
            .build();
    }

    @Override
    public InterviewSession createPersisted() {
        return InterviewSession.builder()
            .id(UUID.fromString("33345678-1234-1234-1234-123456789abc"))
            .slotBooking(slotBookingFactory.createPersisted())
            .sessionLink("existing-link")
            .createdAt(Instant.now().minusSeconds(3600))
            .build();
    }

    @Override
    public InterviewSession createValid() {
        return InterviewSession.builder()
            .id(UUID.randomUUID())
            .slotBooking(slotBookingFactory.createValid())
            .sessionLink("valid-link")
            .createdAt(Instant.now())
            .build();
    }
}