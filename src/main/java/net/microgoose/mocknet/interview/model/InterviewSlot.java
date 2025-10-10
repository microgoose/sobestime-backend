package net.microgoose.mocknet.interview.model;
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
@Table(name = "interview_slot")
public class InterviewSlot {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private InterviewRequest interviewRequest;

    private Instant startTime;

    private Instant endTime;

    private Boolean isBooked;
}
