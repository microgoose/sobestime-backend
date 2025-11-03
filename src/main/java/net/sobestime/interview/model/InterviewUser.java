package net.sobestime.interview.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "interview_user")
public class InterviewUser {

    @Id
    private UUID id;
    private String username;
    private String avatarUrl;
}
