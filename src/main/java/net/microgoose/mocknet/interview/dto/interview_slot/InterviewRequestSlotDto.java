package net.microgoose.mocknet.interview.dto.interview_slot;

import lombok.Builder;
import lombok.Data;
import net.microgoose.mocknet.interview.dto.interview_user.InterviewUserDto;

import java.util.Set;

@Data
@Builder
public class InterviewRequestSlotDto {
    private InterviewUserDto user;
    private Set<SimpleInterviewSlotDto> slots;
}