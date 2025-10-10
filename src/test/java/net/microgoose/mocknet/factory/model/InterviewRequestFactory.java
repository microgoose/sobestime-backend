// InterviewRequestFactory.java
package net.microgoose.mocknet.factory.model;

import net.microgoose.mocknet.factory.ModelTestFactory;
import net.microgoose.mocknet.interview.model.InterviewRequest;

import java.time.Instant;
import java.util.UUID;

public class InterviewRequestFactory implements ModelTestFactory<InterviewRequest> {
    
    private final ProgrammingLanguageFactory programmingLanguageFactory = new ProgrammingLanguageFactory();

    @Override
    public InterviewRequest createNew() {
        return InterviewRequest.builder()
            .title("New Title")
            .description("New Description")
            .creatorId(UUID.randomUUID())
            .programmingLanguage(programmingLanguageFactory.createNew())
            .createdAt(Instant.now())
            .build();
    }

    @Override
    public InterviewRequest createPersisted() {
        return InterviewRequest.builder()
            .title("Existing Title")
            .description("Existing Description")
            .creatorId(UUID.randomUUID())
            .programmingLanguage(programmingLanguageFactory.createPersisted())
            .createdAt(Instant.now().minusSeconds(3600))
            .build();
    }

    @Override
    public InterviewRequest createValid() {
        return InterviewRequest.builder()
            .title("Valid Title")
            .description("Valid Description")
            .creatorId(UUID.randomUUID())
            .programmingLanguage(programmingLanguageFactory.createValid())
            .createdAt(Instant.now())
            .build();
    }
}