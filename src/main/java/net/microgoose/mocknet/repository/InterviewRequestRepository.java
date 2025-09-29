package net.microgoose.mocknet.repository;

import net.microgoose.mocknet.model.InterviewRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InterviewRequestRepository extends JpaRepository<InterviewRequest, UUID> {}
