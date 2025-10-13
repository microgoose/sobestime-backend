package net.microgoose.mocknet.interview.mapper;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.interview.dto.InterviewRoleDto;
import net.microgoose.mocknet.interview.model.InterviewRole;
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
