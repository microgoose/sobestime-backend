package net.microgoose.mocknet.interview.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class InterviewRoleDto {
    private UUID uuid;
    private String name;
}
