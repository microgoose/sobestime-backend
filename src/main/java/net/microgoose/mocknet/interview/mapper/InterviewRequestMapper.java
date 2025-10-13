package net.microgoose.mocknet.interview.mapper;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.interview.dto.InterviewRequestDto;
import net.microgoose.mocknet.interview.model.InterviewRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InterviewRequestMapper {

    private final InterviewSlotMapper interviewSlotMapper;
    private final InterviewRoleMapper interviewRoleMapper;
    private final InterviewUserMapper interviewUserMapper;
    private final SkillMapper skillMapper;
    private final GradeMapper gradeMapper;

    public InterviewRequestDto toDto(InterviewRequest request) {
        return InterviewRequestDto.builder()
            .uuid(request.getId())
            .description(request.getDescription())
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

}
