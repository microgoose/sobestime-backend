package net.microgoose.mocknet.interview.repository;

import net.microgoose.mocknet.interview.model.InterviewRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InterviewRequestRepository extends JpaRepository<InterviewRequest, UUID> {}
