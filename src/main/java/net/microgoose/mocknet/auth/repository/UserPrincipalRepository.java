package net.microgoose.mocknet.auth.repository;

import net.microgoose.mocknet.auth.model.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPrincipalRepository extends JpaRepository<UserPrincipal, UUID> {
    Optional<UserPrincipal> findByEmail(String email);

    boolean existsByEmail(String email);
}
