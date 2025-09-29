package net.microgoose.mocknet.repository;

import net.microgoose.mocknet.model.InterviewSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface InterviewSlotRepository extends JpaRepository<InterviewSlot, UUID> {
    List<InterviewSlot> findByIsBookedFalseAndStartTimeAfter(Instant dateTime);
}
