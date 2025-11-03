package net.sobestime.interview.repository;

import net.sobestime.interview.model.InterviewRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InterviewRoleRepository extends JpaRepository<InterviewRole, UUID> {
    boolean existsByName(String name);
}
