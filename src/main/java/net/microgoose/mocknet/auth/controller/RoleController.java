package net.microgoose.mocknet.auth.controller;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.auth.dto.CreateRoleRequest;
import net.microgoose.mocknet.auth.model.Role;
import net.microgoose.mocknet.auth.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public List<Role> getAll() {
        return roleService.getAll();
    }

    @PostMapping
    public Role create(@RequestBody CreateRoleRequest request) {
        return roleService.createRole(request);
    }

}
