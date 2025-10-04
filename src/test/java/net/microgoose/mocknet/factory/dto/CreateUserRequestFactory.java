package net.microgoose.mocknet.factory.dto;

import net.microgoose.mocknet.factory.DataTestFactory;
import net.microgoose.mocknet.user.dto.CreateUserRequest;

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
