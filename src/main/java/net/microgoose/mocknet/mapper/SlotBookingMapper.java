package net.microgoose.mocknet.mapper;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.config.DateTimeService;
import net.microgoose.mocknet.dto.CreateSlotBookingRequest;
import net.microgoose.mocknet.dto.SlotBookingDto;
import net.microgoose.mocknet.model.InterviewSlot;
import net.microgoose.mocknet.model.SlotBooking;
import net.microgoose.mocknet.model.User;
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
            .interviewer(User.builder().id(request.getInterviewerId()).build())
            .bookedAt(Instant.now())
            .build();
    }

    public SlotBookingDto toDto(SlotBooking request) {
        return SlotBookingDto.builder()
            .id(request.getId())
            .slotId(request.getSlot().getId())
            .interviewerId(request.getInterviewer().getId())
            .bookedAt(dateTimeService.toOffsetDateTime(request.getBookedAt()))
            .build();
    }

    public List<SlotBookingDto> toDto(List<SlotBooking> request) {
        return request.stream().map(this::toDto).toList();
    }
}
