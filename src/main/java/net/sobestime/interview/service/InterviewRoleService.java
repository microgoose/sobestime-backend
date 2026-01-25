package net.sobestime.interview.service;

import lombok.RequiredArgsConstructor;
import net.sobestime.app.exception.ValidationException;
import net.sobestime.interview.dto.interview_role.CreateInterviewRoleRequest;
import net.sobestime.interview.model.InterviewRole;
import net.sobestime.interview.repository.InterviewRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static net.sobestime.interview.config.MessageDictionary.ROLE_ALREADY_EXIST;
import static net.sobestime.interview.config.MessageDictionary.ROLE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class InterviewRoleService {

    private final InterviewRoleRepository roleRepository;

    public List<InterviewRole> getAll() {
        return roleRepository.findAll();
    }

    public InterviewRole getById(UUID roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new ValidationException(ROLE_NOT_FOUND));
    }

    public InterviewRole save(CreateInterviewRoleRequest request) {
        if (roleRepository.existsByName(request.getName()))
            throw new ValidationException(ROLE_ALREADY_EXIST);

        return roleRepository.save(InterviewRole.builder()
            .name(request.getName())
            .build());
    }

}