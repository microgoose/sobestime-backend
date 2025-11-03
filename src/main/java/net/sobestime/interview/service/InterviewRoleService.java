package net.sobestime.interview.service;

import lombok.RequiredArgsConstructor;
import net.sobestime.app.exception.ValidationException;
import net.sobestime.interview.dto.interview_role.CreateInterviewRoleRequest;
import net.sobestime.interview.model.InterviewRole;
import net.sobestime.interview.repository.InterviewRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.sobestime.interview.config.MessageDictionary.ROLE_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
public class InterviewRoleService {

    private final InterviewRoleRepository repository;

    public List<InterviewRole> getAll() {
        return repository.findAll();
    }

    public InterviewRole save(CreateInterviewRoleRequest request) {
        if (repository.existsByName(request.getName()))
            throw new ValidationException(ROLE_ALREADY_EXIST);

        return repository.save(InterviewRole.builder()
            .name(request.getName())
            .build());
    }

}