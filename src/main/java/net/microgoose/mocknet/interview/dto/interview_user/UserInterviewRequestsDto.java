package net.microgoose.mocknet.interview.dto.interview_user;

import lombok.Builder;
import lombok.Data;
import net.microgoose.mocknet.interview.dto.StatusDto;
import net.microgoose.mocknet.interview.dto.grade.GradeDto;
import net.microgoose.mocknet.interview.dto.skill.SkillDto;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class UserInterviewRequestsDto {
    private UUID uuid;
    private String title;
    private Set<GradeDto> gradeUuids;
    private Set<SkillDto> skillUuids;
    private StatusDto status;
    private int slotsCount;
    //TODO private int newSlotsCount;
}
