package net.sobestime.interview.dto.interview_user;

import lombok.Builder;
import lombok.Data;
import net.sobestime.interview.dto.StatusDto;
import net.sobestime.interview.dto.grade.GradeDto;
import net.sobestime.interview.dto.skill.SkillDto;

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
