package net.microgoose.mocknet.auth.controller;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.auth.dto.CreateRoleRequest;
import net.microgoose.mocknet.auth.model.Role;
import net.microgoose.mocknet.auth.service.RoleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public Role create(@RequestBody CreateRoleRequest request) {
        return roleService.createRole(request);
    }

}
