package net.microgoose.mocknet.factory.dto;

import net.microgoose.mocknet.auth.dto.UpdateUserRequest;
import net.microgoose.mocknet.factory.DataTestFactory;

import java.util.UUID;

public class UpdateUserRequestFactory implements DataTestFactory<UpdateUserRequest> {

    @Override
    public UpdateUserRequest createNew() {
        return UpdateUserRequest.builder()
            .id(UUID.randomUUID())
            .username("newupdated")
            .email("newupdated@example.com")
            .build();
    }

    @Override
    public UpdateUserRequest createValid() {
        return UpdateUserRequest.builder()
            .id(UUID.randomUUID())
            .username("updateduser")
            .email("updated@example.com")
            .build();
    }
}
