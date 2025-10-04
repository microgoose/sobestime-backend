package net.microgoose.mocknet.interview.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterviewRequestDto {
    private UUID id;
    private UUID creatorId;
    private UUID programmingLanguageId;
    private String title;
    private String description;
    private OffsetDateTime createdAt;
}