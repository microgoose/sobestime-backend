package net.microgoose.mocknet.factory.dto;

import net.microgoose.mocknet.dto.CreateProgrammingLanguageRequest;
import net.microgoose.mocknet.factory.DataTestFactory;

public class CreateProgrammingLanguageRequestFactory implements DataTestFactory<CreateProgrammingLanguageRequest> {

    @Override
    public CreateProgrammingLanguageRequest createNew() {
        return CreateProgrammingLanguageRequest.builder()
            .name("New Language Request")
            .build();
    }

    @Override
    public CreateProgrammingLanguageRequest createValid() {
        return CreateProgrammingLanguageRequest.builder()
            .name("Java")
            .build();
    }
}
