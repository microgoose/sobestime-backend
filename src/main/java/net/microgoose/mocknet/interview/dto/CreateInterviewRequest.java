package net.microgoose.mocknet.interview.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import net.microgoose.mocknet.interview.config.MessageDictionary;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class CreateInterviewRequest {

    private String description;

    @NotNull(message = MessageDictionary.ROLE_NOT_SPECIFIED)
    private UUID roleUuid;

    @NotEmpty(message = MessageDictionary.GRADES_NOT_SPECIFIED)
    private Set<UUID> gradeUuids;

    @NotEmpty(message = MessageDictionary.SKILLS_NOT_SPECIFIED)
    private Set<UUID> skillUuids;

    @NotEmpty(message = MessageDictionary.SLOTS_NOT_SPECIFIED)
    private Set<OffsetDateTime> slots;
}