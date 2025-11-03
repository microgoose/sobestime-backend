package net.sobestime.interview.dto.interview_slot;

import lombok.Builder;
import lombok.Data;
import net.sobestime.interview.dto.StatusDto;
import net.sobestime.interview.dto.interview_user.InterviewUserDto;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class InterviewSlotDto {
    private UUID uuid;
    private OffsetDateTime startTime;
    private StatusDto status;
    private InterviewUserDto booker;
}
