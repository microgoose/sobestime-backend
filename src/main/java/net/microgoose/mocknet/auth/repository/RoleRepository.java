package net.microgoose.mocknet.auth.repository;

import net.microgoose.mocknet.auth.model.Role;
import net.microgoose.mocknet.auth.model.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);

    Set<Role> findByUsersContains(UserPrincipal user);

    boolean existsByName(String name);
}
