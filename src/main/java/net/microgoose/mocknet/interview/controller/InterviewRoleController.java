package net.microgoose.mocknet.interview.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.interview.dto.CreateInterviewRoleRequest;
import net.microgoose.mocknet.interview.model.InterviewRole;
import net.microgoose.mocknet.interview.service.InterviewRoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/interview-roles")
@RequiredArgsConstructor
public class InterviewRoleController {

    private final InterviewRoleService interviewRoleService;

    @PostMapping
    public InterviewRole createInterviewRole(@RequestBody @Valid CreateInterviewRoleRequest request) {
        return interviewRoleService.save(request);
    }

    @GetMapping
    public List<InterviewRole> getAllInterviewRoles() {
        return interviewRoleService.getAll();
    }
}