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
@Table(name = "slot_bookings")
public class SlotBooking {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private InterviewSlot slot;

    @ManyToOne
    private User interviewer;

    private Instant bookedAt;
}
