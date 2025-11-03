package net.sobestime.auth.service;

import lombok.RequiredArgsConstructor;
import net.sobestime.app.exception.NotFoundException;
import net.sobestime.app.exception.ValidationException;
import net.sobestime.auth.dto.CreateRoleRequest;
import net.sobestime.auth.model.Role;
import net.sobestime.auth.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.sobestime.auth.config.MessageDictionary.*;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public Role findByName(String name) {
        return repository.findByName(name)
            .orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND));
    }

    public List<Role> getAll() {
        return repository.findAll();
    }

    public Role createRole(CreateRoleRequest request) {
        if (!request.getName().startsWith("ROLE_"))
            throw new ValidationException(ROLE_INCORRECT_PREFIX);
        if (repository.existsByName(request.getName()))
            throw new ValidationException(String.format(ROLE_ALREADY_EXIST, request.getName()));

        return repository.save(Role.builder()
            .name(request.getName())
            .description(request.getDescription())
            .build());
    }

}