package net.microgoose.mocknet.interview.repository;

import net.microgoose.mocknet.interview.model.InterviewUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InterviewUserRepository extends JpaRepository<InterviewUser, UUID> {}
