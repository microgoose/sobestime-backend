// SlotBookingFactory.java
package net.microgoose.mocknet.factory.model;

import net.microgoose.mocknet.factory.ModelTestFactory;
import net.microgoose.mocknet.model.SlotBooking;

import java.time.Instant;
import java.util.UUID;

public class SlotBookingFactory implements ModelTestFactory<SlotBooking> {
    
    private final InterviewSlotFactory interviewSlotFactory = new InterviewSlotFactory();
    private final UserFactory userFactory = new UserFactory();

    @Override
    public SlotBooking createNew() {
        return SlotBooking.builder()
            .id(UUID.randomUUID())
            .slot(interviewSlotFactory.createNew())
            .interviewer(userFactory.createNew())
            .bookedAt(Instant.now())
            .build();
    }

    @Override
    public SlotBooking createPersisted() {
        return SlotBooking.builder()
            .id(UUID.fromString("88345678-1234-1234-1234-123456789abc"))
            .slot(interviewSlotFactory.createPersisted())
            .interviewer(userFactory.createPersisted())
            .bookedAt(Instant.now().minusSeconds(3600))
            .build();
    }

    @Override
    public SlotBooking createValid() {
        return SlotBooking.builder()
            .id(UUID.randomUUID())
            .slot(interviewSlotFactory.createValid())
            .interviewer(userFactory.createValid())
            .bookedAt(Instant.now())
            .build();
    }
}

