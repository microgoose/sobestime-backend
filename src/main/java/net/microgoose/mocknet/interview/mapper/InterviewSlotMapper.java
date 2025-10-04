package net.microgoose.mocknet.interview.mapper;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.config.DateTimeService;
import net.microgoose.mocknet.interview.dto.CreateInterviewSlotRequest;
import net.microgoose.mocknet.interview.dto.InterviewSlotDto;
import net.microgoose.mocknet.interview.model.InterviewRequest;
import net.microgoose.mocknet.interview.model.InterviewSlot;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InterviewSlotMapper {

    private final DateTimeService dateTimeService;

    public InterviewSlot fromDto(CreateInterviewSlotRequest request) {
        return InterviewSlot.builder()
            .interviewRequest(InterviewRequest.builder().id(request.getInterviewRequestId()).build())
            .startTime(request.getStartTime().toInstant())
            .endTime(request.getEndTime().toInstant())
            .build();
    }

    public InterviewSlot fromDto(InterviewSlotDto request) {
        return InterviewSlot.builder()
            .id(request.getId())
            .interviewRequest(InterviewRequest.builder().id(request.getInterviewRequestId()).build())
            .startTime(request.getStartTime().toInstant())
            .endTime(request.getEndTime().toInstant())
            .isBooked(request.isBooked())
            .build();
    }

    public InterviewSlotDto toDto(InterviewSlot request) {
        return InterviewSlotDto.builder()
            .id(request.getId())
            .interviewRequestId(request.getInterviewRequest().getId())
            .startTime(dateTimeService.toOffsetDateTime(request.getStartTime()))
            .endTime(dateTimeService.toOffsetDateTime(request.getEndTime()))
            .isBooked(request.getIsBooked() != null && request.getIsBooked())
            .build();
    }

    public List<InterviewSlotDto> toDto(List<InterviewSlot> request) {
        return request.stream().map(this::toDto).toList();
    }

}
