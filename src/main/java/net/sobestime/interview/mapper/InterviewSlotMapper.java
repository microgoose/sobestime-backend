package net.sobestime.interview.mapper;

import lombok.RequiredArgsConstructor;
import net.sobestime.app.config.DateTimeService;
import net.sobestime.interview.dto.interview_slot.InterviewSlotDto;
import net.sobestime.interview.dto.interview_slot.SimpleInterviewSlotDto;
import net.sobestime.interview.model.InterviewSlot;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InterviewSlotMapper {

    private final DateTimeService dateTimeService;
    private final InterviewUserMapper interviewUserMapper;
    private final StatusMapper statusMapper;

    public InterviewSlotDto toDto(InterviewSlot slot) {
        return InterviewSlotDto.builder()
            .uuid(slot.getId())
            .status(statusMapper.toDto(slot.getStatus()))
            .startTime(dateTimeService.toOffsetDateTime(slot.getStartTime()))
            .booker(interviewUserMapper.toDto(slot.getBooker()))
            .build();
    }

    public SimpleInterviewSlotDto toSimpleDto(InterviewSlot slot) {
        return SimpleInterviewSlotDto.builder()
            .uuid(slot.getId())
            .status(statusMapper.toDto(slot.getStatus()))
            .startTime(dateTimeService.toOffsetDateTime(slot.getStartTime()))
            .build();
    }

    public Set<InterviewSlotDto> toDto(Set<InterviewSlot> items) {
        return items.stream().map(this::toDto).collect(Collectors.toSet());
    }

    public List<InterviewSlotDto> toDto(List<InterviewSlot> items) {
        return items.stream().map(this::toDto).collect(Collectors.toList());
    }
}
