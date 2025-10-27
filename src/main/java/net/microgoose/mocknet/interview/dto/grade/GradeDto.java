package net.microgoose.mocknet.interview.dto.grade;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GradeDto {
    private UUID uuid;
    private String name;
}
