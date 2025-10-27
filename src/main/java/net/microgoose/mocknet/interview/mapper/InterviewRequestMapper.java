package net.microgoose.mocknet.interview.mapper;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.interview.dto.interview_request.InterviewRequestDto;
import net.microgoose.mocknet.interview.dto.interview_user.UserInterviewRequestsDto;
import net.microgoose.mocknet.interview.model.InterviewRequest;
import net.microgoose.mocknet.interview.model.InterviewRequestProjection;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InterviewRequestMapper {

    private final InterviewSlotMapper interviewSlotMapper;
    private final InterviewRoleMapper interviewRoleMapper;
    private final InterviewUserMapper interviewUserMapper;
    private final StatusMapper statusMapper;
    private final SkillMapper skillMapper;
    private final GradeMapper gradeMapper;

    public InterviewRequestDto toDto(InterviewRequest request) {
        return InterviewRequestDto.builder()
            .uuid(request.getId())
            .title(request.getTitle())
            .description(request.getDescription())
            .status(statusMapper.toDto(request.getStatus()))
            .user(interviewUserMapper.toDto(request.getCreator()))
            .role(interviewRoleMapper.toDto(request.getRole()))
            .skillUuids(skillMapper.toDto(request.getSkills()))
            .gradeUuids(gradeMapper.toDto(request.getGrades()))
            .slots(interviewSlotMapper.toDto(request.getSlots()))
            .build();
    }

    public Page<InterviewRequestDto> toDto(Page<InterviewRequest> items) {
        return items.map(this::toDto);
    }

    public UserInterviewRequestsDto toUserRequestDto(InterviewRequestProjection projection) {
        InterviewRequest request = projection.getInterviewRequest();
        return UserInterviewRequestsDto.builder()
            .uuid(request.getId())
            .title(request.getTitle())
            .skillUuids(skillMapper.toDto(request.getSkills()))
            .gradeUuids(gradeMapper.toDto(request.getGrades()))
            .status(statusMapper.toDto(request.getStatus()))
            .slotsCount(projection.getSlotsCount())
            .build();
    }

    public Page<UserInterviewRequestsDto> toUserRequestDto(Page<InterviewRequestProjection> items) {
        return items.map(this::toUserRequestDto);
    }

}
