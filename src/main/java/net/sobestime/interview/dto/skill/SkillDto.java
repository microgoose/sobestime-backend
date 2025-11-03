package net.sobestime.interview.dto.skill;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SkillDto {
    private UUID uuid;
    private String name;
}
