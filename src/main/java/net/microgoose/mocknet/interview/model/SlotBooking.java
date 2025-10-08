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
@Table(name = "slot_bookings")
public class SlotBooking {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private InterviewSlot slot;

    private UUID interviewerId;

    private Instant bookedAt;
}
