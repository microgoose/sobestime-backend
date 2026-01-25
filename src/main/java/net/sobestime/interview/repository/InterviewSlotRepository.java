package net.sobestime.interview.repository;

import net.sobestime.interview.model.InterviewSlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface InterviewSlotRepository extends JpaRepository<InterviewSlot, UUID> {

    boolean existsByRequest_IdAndBooker_IdAndStartTime(UUID requestId, UUID bookerId, Instant startTime);

    @Query("SELECT islot FROM InterviewSlot islot " +
        "LEFT JOIN FETCH islot.booker " +
        "WHERE islot.request.id = :requestId")
    Page<InterviewSlot> findByRequestIdWithBooker(@Param("requestId") UUID requestId, Pageable pageable);

    @Query("SELECT islot FROM InterviewSlot islot " +
        "LEFT JOIN FETCH islot.request " +
        "WHERE islot.booker.id = :bookerId")
    Page<InterviewSlot> findByBooker_Id(@Param("bookerId") UUID bookerId, Pageable pageable);

    Page<InterviewSlot> findByRequest_Creator_Id(UUID creatorId, Pageable pageable);

    List<InterviewSlot> findByRequest_Id(UUID requestId);
}
