package net.microgoose.mocknet.factory.dto;

import net.microgoose.mocknet.dto.CreateUserRequest;
import net.microgoose.mocknet.factory.DataTestFactory;

public class CreateUserRequestFactory implements DataTestFactory<CreateUserRequest> {

    @Override
    public CreateUserRequest createNew() {
        return CreateUserRequest.builder()
            .username("newrequest")
            .email("newrequest@example.com")
            .build();
    }

    @Override
    public CreateUserRequest createValid() {
        return CreateUserRequest.builder()
            .username("validuser")
            .email("valid@example.com")
            .build();
    }
}
