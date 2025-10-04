// InterviewRequestDtoFactory.java
package net.microgoose.mocknet.factory.dto;

import net.microgoose.mocknet.factory.DataTestFactory;
import net.microgoose.mocknet.interview.dto.InterviewRequestDto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class InterviewRequestDtoFactory implements DataTestFactory<InterviewRequestDto> {
    
    @Override
    public InterviewRequestDto createNew() {
        return InterviewRequestDto.builder()
            .title("New DTO Title")
            .description("New DTO Description")
            .creatorId(UUID.randomUUID())
            .programmingLanguageId(UUID.randomUUID())
            .createdAt(OffsetDateTime.now())
            .build();
    }

    @Override
    public InterviewRequestDto createValid() {
        return InterviewRequestDto.builder()
            .title("Valid DTO Title")
            .description("Valid DTO Description")
            .creatorId(UUID.randomUUID())
            .programmingLanguageId(UUID.randomUUID())
            .createdAt(OffsetDateTime.now())
            .build();
    }
}
