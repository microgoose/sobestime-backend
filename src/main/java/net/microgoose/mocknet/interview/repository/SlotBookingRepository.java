package net.microgoose.mocknet.interview.repository;

import net.microgoose.mocknet.interview.model.SlotBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SlotBookingRepository extends JpaRepository<SlotBooking, UUID> {
    Optional<SlotBooking> findBySlotId(UUID slotId);
    List<SlotBooking> findByInterviewerId(UUID interviewerId);
}
