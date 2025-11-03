package net.sobestime.interview.dto.interview_slot;

import lombok.Builder;
import lombok.Data;
import net.sobestime.interview.dto.interview_user.InterviewUserDto;

import java.util.Set;

@Data
@Builder
public class InterviewRequestSlotDto {
    private InterviewUserDto user;
    private Set<SimpleInterviewSlotDto> slots;
}