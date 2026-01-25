package net.sobestime.interview.repository;

import net.sobestime.interview.model.ScheduledInterview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScheduledInterviewRepository extends JpaRepository<ScheduledInterview, UUID> {
    @Query("select si from ScheduledInterview si " +
        "where si.request.creator.id = :userId or si.slot.booker.id = :userId")
    Page<ScheduledInterview> findUserInterviews(@Param("userId") UUID userId, Pageable pageable);

    Optional<ScheduledInterview> findByRequest_Id(UUID requestId);
}
