package net.microgoose.mocknet.interview.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusDto {
    private String name;
    private String description;
}
