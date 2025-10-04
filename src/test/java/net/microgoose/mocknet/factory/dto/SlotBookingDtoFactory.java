package net.microgoose.mocknet.factory.dto;

import net.microgoose.mocknet.factory.DataTestFactory;
import net.microgoose.mocknet.interview.dto.SlotBookingDto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class SlotBookingDtoFactory implements DataTestFactory<SlotBookingDto> {

    @Override
    public SlotBookingDto createNew() {
        return SlotBookingDto.builder()
            .id(UUID.randomUUID())
            .slotId(UUID.randomUUID())
            .interviewerId(UUID.randomUUID())
            .bookedAt(OffsetDateTime.now())
            .build();
    }

    @Override
    public SlotBookingDto createValid() {
        return SlotBookingDto.builder()
            .id(UUID.randomUUID())
            .slotId(UUID.randomUUID())
            .interviewerId(UUID.randomUUID())
            .bookedAt(OffsetDateTime.now())
            .build();
    }
}
