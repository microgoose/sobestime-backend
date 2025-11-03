package net.sobestime.interview.mapper;

import lombok.RequiredArgsConstructor;
import net.sobestime.interview.dto.interview_role.InterviewRoleDto;
import net.sobestime.interview.model.InterviewRole;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InterviewRoleMapper {

    public InterviewRoleDto toDto(InterviewRole role) {
        return InterviewRoleDto.builder()
            .uuid(role.getId())
            .name(role.getName())
            .build();
    }
}
