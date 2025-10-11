package net.microgoose.mocknet.auth.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.exception.NotFoundException;
import net.microgoose.mocknet.app.exception.ValidationException;
import net.microgoose.mocknet.auth.dto.CreateRoleRequest;
import net.microgoose.mocknet.auth.dto.RoleDto;
import net.microgoose.mocknet.auth.model.Role;
import net.microgoose.mocknet.auth.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public Role findByName(String name) {
        return repository.findByName(name)
            .orElseThrow(() -> new NotFoundException("Не удалось найти роль с именем: " + name));
    }

    public RoleDto createRole(CreateRoleRequest request) {
        if (!StringUtils.hasText(request.getName()))
            throw new ValidationException("Не указано имя роли");
        if (!request.getName().startsWith("ROLE_"))
            throw new ValidationException("Некорректный префикс роли");
        if (!StringUtils.hasText(request.getDescription()))
            throw new ValidationException("Не указано описание роли");
        if (repository.existsByName(request.getName()))
            throw new ValidationException("Роль с именем '" + request.getName() + "' уже существует");

        Role role = repository.save(Role.builder()
            .name(request.getName())
            .description(request.getDescription())
            .build());

        return RoleDto.builder()
            .id(role.getId())
            .name(role.getName())
            .description(request.getDescription())
            .build();
    }

}