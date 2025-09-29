package net.microgoose.mocknet.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "interview_requests")
public class InterviewRequest {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User creator;

    @ManyToOne
    private ProgrammingLanguage programmingLanguage;

    private String title;

    private String description;

    private Instant createdAt;
}
