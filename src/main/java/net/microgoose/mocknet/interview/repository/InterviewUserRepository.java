package net.microgoose.mocknet.interview.repository;

import net.microgoose.mocknet.interview.model.InterviewUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InterviewUserRepository extends JpaRepository<InterviewUser, UUID> {

    @Query("select ir.creator.id from InterviewRequest ir where ir.id = :interviewRequestUuid")
    UUID findUserIdByRequestId(@Param("interviewRequestUuid") UUID interviewRequestUuid);
}
