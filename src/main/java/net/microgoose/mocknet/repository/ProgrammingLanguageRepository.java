package net.microgoose.mocknet.repository;

import net.microgoose.mocknet.model.ProgrammingLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProgrammingLanguageRepository extends JpaRepository<ProgrammingLanguage, UUID> {
    boolean existsByName(String name);
}
