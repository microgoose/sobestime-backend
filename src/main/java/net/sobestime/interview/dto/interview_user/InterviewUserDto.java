package net.sobestime.interview.dto.interview_user;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class InterviewUserDto {
    private UUID uuid;
    private String username;
    private String avatarUrl;
}
