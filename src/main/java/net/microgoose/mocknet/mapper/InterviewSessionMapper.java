package net.microgoose.mocknet.mapper;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.config.DateTimeService;
import net.microgoose.mocknet.dto.InterviewSessionDto;
import net.microgoose.mocknet.model.InterviewSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InterviewSessionMapper {

    private final DateTimeService dateTimeService;

    public InterviewSessionDto toDto(InterviewSession request) {
        return InterviewSessionDto.builder()
            .id(request.getId())
            .slotBookingId(request.getSlotBooking().getId())
            .sessionLink(request.getSessionLink())
            .createdAt(dateTimeService.toOffsetDateTime(request.getCreatedAt()))
            .build();
    }

    public List<InterviewSessionDto> toDto(List<InterviewSession> request) {
        return request.stream().map(this::toDto).toList();
    }

}
