package net.microgoose.mocknet.interview.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import net.microgoose.mocknet.interview.config.ErrorDictionary;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class CreateInterviewRequest {

    private String description;

    @NotNull(message = ErrorDictionary.ROLE_NOT_SPECIFIED)
    private UUID roleUuid;

    @NotEmpty(message = ErrorDictionary.GRADES_NOT_SPECIFIED)
    private Set<UUID> gradeUuids;

    @NotEmpty(message = ErrorDictionary.SKILLS_NOT_SPECIFIED)
    private Set<UUID> skillUuids;

    @NotEmpty(message = ErrorDictionary.SLOTS_NOT_SPECIFIED)
    private Set<OffsetDateTime> slots;
}