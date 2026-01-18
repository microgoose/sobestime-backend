package net.sobestime.interview.service;

import lombok.RequiredArgsConstructor;
import net.sobestime.app.exception.ValidationException;
import net.sobestime.interview.dto.interview_request.CreateInterviewRequest;
import net.sobestime.interview.dto.interview_request.InterviewRequestDto;
import net.sobestime.interview.mapper.InterviewRequestMapper;
import net.sobestime.interview.model.*;
import net.sobestime.interview.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static net.sobestime.interview.config.MessageDictionary.*;

@Service
@RequiredArgsConstructor
public class InterviewRequestService {

    private final InterviewRequestRepository repository;
    private final InterviewUserRepository userRepository;
    private final InterviewRoleRepository roleRepository;
    private final GradeRepository gradeRepository;
    private final SkillRepository skillRepository;

    private final InterviewRequestMapper mapper;

    // TODO Serializing PageImpl instances as-is is not supported,
    //  meaning that there is no guarantee about the stability of the resulting JSON structure!
    public Page<InterviewRequestDto> findAll(Pageable pageable) {
        return mapper.toDto(repository.findAll(pageable));
    }

    public InterviewRequestDto findById(UUID requestId) {
        return mapper.toDto(repository.findById(requestId)
            .orElseThrow(() -> new ValidationException(INTERVIEW_REQUEST_NOT_FOUND)));
    }

    @Transactional
    public InterviewRequestDto saveRequest(UUID creatorId, CreateInterviewRequest request) {
        InterviewUser user = userRepository.findById(creatorId)
            .orElseThrow(() -> new ValidationException(USER_NOT_FOUND));
        InterviewRole role = roleRepository.findById(request.getRoleUuid())
            .orElseThrow(() -> new ValidationException(ROLE_NOT_FOUND));

        List<Skill> skills = skillRepository.findAllById(request.getSkillUuids());

        if (skills.size() != request.getSkillUuids().size())
            throw new ValidationException(SKILLS_NOT_FOUND);

        List<Grade> grades = gradeRepository.findAllById(request.getGradeUuids());

        if (grades.size() != request.getGradeUuids().size())
            throw new ValidationException(GRADES_NOT_FOUND);

        // TODO  Валидировать описание: проверить на запрещённые символы

        InterviewRequest interviewRequest = repository.save(InterviewRequest.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .status(InterviewRequestStatus.NEW)
            .creator(user)
            .role(role)
            .grades(new HashSet<>(grades))
            .skills(new HashSet<>(skills))
            .slots(new HashSet<>())
            .build());

        return mapper.toDto(interviewRequest);
    }

    // todo cancel request
}