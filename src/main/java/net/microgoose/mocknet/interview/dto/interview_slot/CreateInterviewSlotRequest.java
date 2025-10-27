package net.microgoose.mocknet.interview.dto.interview_slot;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import net.microgoose.mocknet.interview.config.MessageDictionary;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class CreateInterviewSlotRequest {
    @NotNull(message = MessageDictionary.INTERVIEW_REQUEST_ID_NOT_SPECIFIED)
    private UUID interviewRequestUuid;

    @NotEmpty(message = MessageDictionary.SLOTS_SET_EMPTY)
    private Set<OffsetDateTime> startTimes;
}