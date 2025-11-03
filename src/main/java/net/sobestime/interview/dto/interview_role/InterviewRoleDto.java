package net.sobestime.interview.dto.interview_role;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class InterviewRoleDto {
    private UUID uuid;
    private String name;
}
