package net.microgoose.mocknet.interview.mapper;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.config.DateTimeService;
import net.microgoose.mocknet.interview.dto.interview_user.InterviewUserDto;
import net.microgoose.mocknet.interview.dto.interview_user.UserScheduledInterviewDto;
import net.microgoose.mocknet.interview.model.InterviewRequest;
import net.microgoose.mocknet.interview.model.InterviewSlot;
import net.microgoose.mocknet.interview.model.ScheduledInterview;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledInterviewMapper {

    private final DateTimeService dateTimeService;
    private final InterviewUserMapper userMapper;
    private final StatusMapper statusMapper;

    public UserScheduledInterviewDto toDto(ScheduledInterview scheduledInterview) {
        // TODO incorrect behavior, to many business logic
        InterviewRequest request = scheduledInterview.getRequest();
        InterviewSlot slot = scheduledInterview.getSlot();
        InterviewUserDto creatorDto = userMapper.toDto(request.getCreator());
        InterviewUserDto bookerDto = userMapper.toDto(slot.getBooker());

        return UserScheduledInterviewDto.builder()
            .meetingLink(scheduledInterview.getMeetingLink())
            .status(statusMapper.toDto(scheduledInterview.getStatus()))
            .requestUuid(request.getId())
            .requestTitle(request.getTitle())
            .slotUuid(slot.getId())
            .startTime(dateTimeService.toOffsetDateTime(slot.getStartTime()))
            .participants(List.of(creatorDto, bookerDto))
            .build();
    }

    public Page<UserScheduledInterviewDto> toDto(Page<ScheduledInterview> items) {
        return items.map(this::toDto);
    }
}
