package net.microgoose.mocknet.interview.repository;

import net.microgoose.mocknet.interview.model.InterviewRequest;
import net.microgoose.mocknet.interview.model.InterviewRequestProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InterviewRequestRepository extends JpaRepository<InterviewRequest, UUID> {

    @Query("SELECT ir as interviewRequest, COUNT(s) as slotsCount " +
        "FROM InterviewRequest ir " +
        "LEFT JOIN ir.slots s " +
        "WHERE ir.creator.id = :creatorId " +
        "GROUP BY ir")
    Page<InterviewRequestProjection> findByCreator_Id(@Param("creatorId") UUID creatorId, Pageable pageable);

    @Query("SELECT ir FROM InterviewRequest ir LEFT JOIN ir.slots s WHERE s.id = :slotId")
    Optional<InterviewRequest> findBySlotId(@Param("slotId") UUID slotId);
}
