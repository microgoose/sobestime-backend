package net.microgoose.mocknet.interview.repository;

import net.microgoose.mocknet.interview.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GradeRepository extends JpaRepository<Grade, UUID> {
    boolean existsByName(String name);
}
