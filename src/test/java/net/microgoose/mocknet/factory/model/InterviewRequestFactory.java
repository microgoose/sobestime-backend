// InterviewRequestFactory.java
package net.microgoose.mocknet.factory.model;

import net.microgoose.mocknet.factory.ModelTestFactory;
import net.microgoose.mocknet.model.InterviewRequest;

import java.time.Instant;

public class InterviewRequestFactory implements ModelTestFactory<InterviewRequest> {
    
    private final UserFactory userFactory = new UserFactory();
    private final ProgrammingLanguageFactory programmingLanguageFactory = new ProgrammingLanguageFactory();

    @Override
    public InterviewRequest createNew() {
        return InterviewRequest.builder()
            .title("New Title")
            .description("New Description")
            .creator(userFactory.createNew())
            .programmingLanguage(programmingLanguageFactory.createNew())
            .createdAt(Instant.now())
            .build();
    }

    @Override
    public InterviewRequest createPersisted() {
        return InterviewRequest.builder()
            .title("Existing Title")
            .description("Existing Description")
            .creator(userFactory.createPersisted())
            .programmingLanguage(programmingLanguageFactory.createPersisted())
            .createdAt(Instant.now().minusSeconds(3600))
            .build();
    }

    @Override
    public InterviewRequest createValid() {
        return InterviewRequest.builder()
            .title("Valid Title")
            .description("Valid Description")
            .creator(userFactory.createValid())
            .programmingLanguage(programmingLanguageFactory.createValid())
            .createdAt(Instant.now())
            .build();
    }
}