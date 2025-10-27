package net.microgoose.mocknet.interview.dto.interview_user;

import lombok.Builder;
import lombok.Data;
import net.microgoose.mocknet.interview.dto.interview_request.SimpleInterviewRequestDto;
import net.microgoose.mocknet.interview.dto.interview_slot.SimpleInterviewSlotDto;

import java.util.List;

@Data
@Builder
public class UserInterviewDto {
    private SimpleInterviewRequestDto request;
    private SimpleInterviewSlotDto slot;
    private List<InterviewUserDto> participants;
}
