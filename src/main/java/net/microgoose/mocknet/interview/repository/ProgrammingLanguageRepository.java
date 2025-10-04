package net.microgoose.mocknet.interview.repository;

import net.microgoose.mocknet.interview.model.ProgrammingLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProgrammingLanguageRepository extends JpaRepository<ProgrammingLanguage, UUID> {
    boolean existsByName(String name);
}
