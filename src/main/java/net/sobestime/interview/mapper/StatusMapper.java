package net.sobestime.interview.mapper;

import net.sobestime.interview.dto.StatusDto;
import net.sobestime.interview.model.ConfirmationStatus;
import net.sobestime.interview.model.InterviewRequestStatus;
import net.sobestime.interview.model.ScheduledInterviewStatus;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper {

    public StatusDto toDto(InterviewRequestStatus status) {
        return StatusDto.builder()
            .name(status.name())
            .description(status.getDescription())
            .build();
    }

    public StatusDto toDto(ConfirmationStatus status) {
        return StatusDto.builder()
            .name(status.getDescription())
            .description(status.getDescription())
            .build();
    }

    public StatusDto toDto(ScheduledInterviewStatus status) {
        return StatusDto.builder()
            .name(status.getDescription())
            .description(status.getDescription())
            .build();
    }

}
