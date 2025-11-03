package net.sobestime.interview.dto.interview_role;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateInterviewRoleRequest {

    @NotBlank
    private String name;
}