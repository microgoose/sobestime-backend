package net.microgoose.mocknet.interview.dto.interview_user;

import lombok.Builder;
import lombok.Data;
import net.microgoose.mocknet.interview.dto.StatusDto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserScheduledInterviewDto {
    private String meetingLink;
    private StatusDto status;
    private UUID requestUuid;
    private String requestTitle;
    private UUID slotUuid;
    private OffsetDateTime startTime;
    private List<InterviewUserDto> participants;
}
