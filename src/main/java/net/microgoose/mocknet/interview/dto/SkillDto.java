package net.microgoose.mocknet.interview.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SkillDto {
    private UUID uuid;
    private String name;
}
