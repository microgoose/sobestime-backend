package net.microgoose.mocknet.interview.mapper;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.config.DateTimeService;
import net.microgoose.mocknet.interview.dto.InterviewSlotDto;
import net.microgoose.mocknet.interview.model.InterviewSlot;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InterviewSlotMapper {

    private final DateTimeService dateTimeService;
    private final InterviewUserMapper interviewUserMapper;

    public InterviewSlotDto toDto(InterviewSlot slot) {
        return InterviewSlotDto.builder()
            .uuid(slot.getId())
            .status(slot.getStatus().name())
            .startTime(dateTimeService.toOffsetDateTime(slot.getStartTime()))
            .bookers(interviewUserMapper.toDto(slot.getBookers()))
            .build();
    }

    public Set<InterviewSlotDto> toDto(Set<InterviewSlot> items) {
        return items.stream().map(this::toDto).collect(Collectors.toSet());
    }
}
