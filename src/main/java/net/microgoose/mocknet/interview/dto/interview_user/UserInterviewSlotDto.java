package net.microgoose.mocknet.interview.dto.interview_user;

import lombok.Builder;
import lombok.Data;
import net.microgoose.mocknet.interview.dto.StatusDto;

import java.time.OffsetDateTime;

@Data
@Builder
public class UserInterviewSlotDto {
    private InterviewUserDto creator;
    private String interviewTitle;
    private StatusDto status;
    private OffsetDateTime startTime;
}
