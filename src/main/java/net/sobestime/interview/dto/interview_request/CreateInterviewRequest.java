package net.sobestime.interview.dto.interview_request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import net.sobestime.interview.config.MessageDictionary;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class CreateInterviewRequest {

    @NotEmpty(message = MessageDictionary.TITLE_NOT_SPECIFIED)
    private String title;

    private String description;

    @NotNull(message = MessageDictionary.ROLE_NOT_SPECIFIED)
    private UUID roleUuid;

    @NotEmpty(message = MessageDictionary.GRADES_NOT_SPECIFIED)
    private Set<UUID> gradeUuids;

    @NotEmpty(message = MessageDictionary.SKILLS_NOT_SPECIFIED)
    private Set<UUID> skillUuids;
}