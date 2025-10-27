package net.microgoose.mocknet.interview.dto.grade;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateGradeRequest {

    @NotBlank
    private String name;
}