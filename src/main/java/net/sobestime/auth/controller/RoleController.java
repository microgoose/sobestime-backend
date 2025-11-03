package net.sobestime.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.sobestime.auth.dto.CreateRoleRequest;
import net.sobestime.auth.model.Role;
import net.sobestime.auth.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Роли пользователя")
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Получить все")
    @GetMapping
    public List<Role> getAll() {
        return roleService.getAll();
    }

    @Operation(summary = "Создать роль")
    @PostMapping
    public Role create(@RequestBody CreateRoleRequest request) {
        return roleService.createRole(request);
    }

}
