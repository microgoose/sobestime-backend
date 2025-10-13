package net.microgoose.mocknet.interview.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class InterviewRequestDto {
    private UUID uuid;
    private String description;
    private InterviewUserDto user;
    private InterviewRoleDto role;
    private Set<GradeDto> gradeUuids;
    private Set<SkillDto> skillUuids;
    private Set<InterviewSlotDto> slots;
}