package net.microgoose.mocknet.interview.mapper;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.config.DateTimeService;
import net.microgoose.mocknet.interview.dto.CreateSlotBookingRequest;
import net.microgoose.mocknet.interview.dto.SlotBookingDto;
import net.microgoose.mocknet.interview.model.InterviewSlot;
import net.microgoose.mocknet.interview.model.SlotBooking;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SlotBookingMapper {

    private final DateTimeService dateTimeService;

    public SlotBooking fromDto(CreateSlotBookingRequest request) {
        return SlotBooking.builder()
            .slot(InterviewSlot.builder().id(request.getSlotId()).build())
            .interviewerId(request.getInterviewerId())
            .bookedAt(Instant.now())
            .build();
    }

    public SlotBookingDto toDto(SlotBooking request) {
        return SlotBookingDto.builder()
            .id(request.getId())
            .slotId(request.getSlot().getId())
            .interviewerId(request.getInterviewerId())
            .bookedAt(dateTimeService.toOffsetDateTime(request.getBookedAt()))
            .build();
    }

    public List<SlotBookingDto> toDto(List<SlotBooking> request) {
        return request.stream().map(this::toDto).toList();
    }
}
