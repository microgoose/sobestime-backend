package net.microgoose.mocknet.interview.dto.interview_user;

import lombok.Builder;
import lombok.Data;
import net.microgoose.mocknet.interview.dto.StatusDto;
import net.microgoose.mocknet.interview.dto.interview_slot.SimpleInterviewSlotDto;

import java.util.List;

@Data
@Builder
public class UserInterviewReservationsDto {
    private InterviewUserDto creator;
    private String interviewTitle;
    private StatusDto status;
    private List<SimpleInterviewSlotDto> slots;
}
