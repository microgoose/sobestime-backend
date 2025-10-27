package net.microgoose.mocknet.interview.dto.interview_request;

import lombok.Builder;
import lombok.Data;
import net.microgoose.mocknet.interview.dto.StatusDto;
import net.microgoose.mocknet.interview.dto.grade.GradeDto;
import net.microgoose.mocknet.interview.dto.interview_role.InterviewRoleDto;
import net.microgoose.mocknet.interview.dto.interview_slot.InterviewSlotDto;
import net.microgoose.mocknet.interview.dto.interview_user.InterviewUserDto;
import net.microgoose.mocknet.interview.dto.skill.SkillDto;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class InterviewRequestDto {
    private UUID uuid;
    private String title;
    private String description;
    private StatusDto status;
    private InterviewUserDto user;
    private InterviewRoleDto role;
    private Set<GradeDto> gradeUuids;
    private Set<SkillDto> skillUuids;
    private Set<InterviewSlotDto> slots;
}