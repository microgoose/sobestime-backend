package net.microgoose.mocknet.interview.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Set;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ConfirmationStatus status;

    private Instant startTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private InterviewRequest request;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "interview_slot_booker",
        joinColumns = @JoinColumn(name = "slot_id"),
        inverseJoinColumns = @JoinColumn(name = "booker_id")
    )
    private Set<InterviewUser> bookers;

}
