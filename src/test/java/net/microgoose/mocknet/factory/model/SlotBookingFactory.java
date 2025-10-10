// SlotBookingFactory.java
package net.microgoose.mocknet.factory.model;

import net.microgoose.mocknet.factory.ModelTestFactory;
import net.microgoose.mocknet.interview.model.SlotBooking;

import java.time.Instant;
import java.util.UUID;

public class SlotBookingFactory implements ModelTestFactory<SlotBooking> {
    
    private final InterviewSlotFactory interviewSlotFactory = new InterviewSlotFactory();

    @Override
    public SlotBooking createNew() {
        return SlotBooking.builder()
            .id(UUID.randomUUID())
            .slot(interviewSlotFactory.createNew())
            .interviewerId(UUID.randomUUID())
            .bookedAt(Instant.now())
            .build();
    }

    @Override
    public SlotBooking createPersisted() {
        return SlotBooking.builder()
            .id(UUID.fromString("88345678-1234-1234-1234-123456789abc"))
            .slot(interviewSlotFactory.createPersisted())
            .interviewerId(UUID.fromString("81345678-1234-1234-1234-123456789abc"))
            .bookedAt(Instant.now().minusSeconds(3600))
            .build();
    }

    @Override
    public SlotBooking createValid() {
        return SlotBooking.builder()
            .id(UUID.randomUUID())
            .slot(interviewSlotFactory.createValid())
            .interviewerId(UUID.fromString("8v345678-1234-1234-1234-123456789abc"))
            .bookedAt(Instant.now())
            .build();
    }
}

