package net.microgoose.mocknet.interview.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateInterviewRoleRequest {

    @NotBlank
    private String name;
}