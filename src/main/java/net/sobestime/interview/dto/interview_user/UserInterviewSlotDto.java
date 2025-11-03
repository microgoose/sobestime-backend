package net.sobestime.interview.dto.interview_user;

import lombok.Builder;
import lombok.Data;
import net.sobestime.interview.dto.StatusDto;

import java.time.OffsetDateTime;

@Data
@Builder
public class UserInterviewSlotDto {
    private InterviewUserDto creator;
    private String interviewTitle;
    private StatusDto status;
    private OffsetDateTime startTime;
}
