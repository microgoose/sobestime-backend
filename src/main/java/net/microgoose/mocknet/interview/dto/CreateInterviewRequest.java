package net.microgoose.mocknet.interview.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateInterviewRequest {
    UUID creatorId;
    UUID programmingLanguageId;
    String title;
    String description;
}
