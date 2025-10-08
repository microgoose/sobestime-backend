package net.microgoose.mocknet.auth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRoleRequest {
    private String name;
    private String description;
}
