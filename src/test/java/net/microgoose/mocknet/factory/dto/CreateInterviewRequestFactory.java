package net.microgoose.mocknet.factory.dto;

import net.microgoose.mocknet.factory.DataTestFactory;
import net.microgoose.mocknet.interview.dto.CreateInterviewRequest;

import java.util.UUID;

public class CreateInterviewRequestFactory implements DataTestFactory<CreateInterviewRequest> {

    @Override
    public CreateInterviewRequest createNew() {
        return CreateInterviewRequest.builder()
            .creatorId(UUID.randomUUID())
            .programmingLanguageId(UUID.randomUUID())
            .title("New Create Request Title")
            .description("New Create Request Description")
            .build();
    }

    @Override
    public CreateInterviewRequest createValid() {
        return CreateInterviewRequest.builder()
            .creatorId(UUID.randomUUID())
            .programmingLanguageId(UUID.randomUUID())
            .title("Valid Title")
            .description("Valid Description")
            .build();
    }
}
