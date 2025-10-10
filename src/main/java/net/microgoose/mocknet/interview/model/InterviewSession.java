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
@Table(name = "interview_session")
public class InterviewSession {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private SlotBooking slotBooking;

    private String sessionLink;

    private Instant createdAt;
}
