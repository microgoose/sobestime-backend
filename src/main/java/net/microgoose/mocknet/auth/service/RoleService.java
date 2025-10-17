package net.microgoose.mocknet.auth.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.exception.NotFoundException;
import net.microgoose.mocknet.app.exception.ValidationException;
import net.microgoose.mocknet.auth.dto.CreateRoleRequest;
import net.microgoose.mocknet.auth.model.Role;
import net.microgoose.mocknet.auth.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.microgoose.mocknet.auth.config.MessageDictionary.*;

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