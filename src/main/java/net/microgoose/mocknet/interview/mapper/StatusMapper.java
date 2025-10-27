package net.microgoose.mocknet.interview.mapper;

import net.microgoose.mocknet.interview.dto.StatusDto;
import net.microgoose.mocknet.interview.model.ConfirmationStatus;
import net.microgoose.mocknet.interview.model.InterviewRequestStatus;
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

}
