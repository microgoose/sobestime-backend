package net.microgoose.mocknet.interview.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.interview.dto.interview_role.CreateInterviewRoleRequest;
import net.microgoose.mocknet.interview.model.InterviewRole;
import net.microgoose.mocknet.interview.service.InterviewRoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/interview-roles")
@RequiredArgsConstructor
@Tag(name = "Роли интервью")
public class InterviewRoleController {

    private final InterviewRoleService interviewRoleService;

    @Operation(summary = "Получить все роли")
    @GetMapping
    public List<InterviewRole> getAllInterviewRoles() {
        return interviewRoleService.getAll();
    }

    @Operation(summary = "Создать роль")
    @PostMapping
    public InterviewRole createInterviewRole(@RequestBody @Valid CreateInterviewRoleRequest request) {
        return interviewRoleService.save(request);
    }
}