package net.microgoose.mocknet.interview.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.exception.ValidationException;
import net.microgoose.mocknet.interview.dto.CreateInterviewRequest;
import net.microgoose.mocknet.interview.dto.InterviewRequestDto;
import net.microgoose.mocknet.interview.dto.InterviewSlotDto;
import net.microgoose.mocknet.interview.mapper.InterviewRequestMapper;
import net.microgoose.mocknet.interview.model.*;
import net.microgoose.mocknet.interview.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.microgoose.mocknet.interview.config.MessageDictionary.*;

@Service
@RequiredArgsConstructor
public class InterviewRequestService {

    private final InterviewRequestRepository repository;
    private final InterviewUserRepository userRepository;
    private final InterviewRoleRepository roleRepository;
    private final GradeRepository gradeRepository;
    private final SkillRepository skillRepository;

    private final InterviewSlotService slotService;

    private final InterviewRequestMapper mapper;

    public Page<InterviewRequestDto> findAll(Pageable pageable) {
        return mapper.toDto(repository.findAll(pageable));
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
            .description(request.getDescription())
            .creator(user)
            .role(role)
            .grades(new HashSet<>(grades))
            .skills(new HashSet<>(skills))
            .slots(new HashSet<>())
            .build());

        Set<InterviewSlotDto> slots = request.getSlots().stream()
            .map(startTime  -> slotService.save(interviewRequest, startTime))
            .collect(Collectors.toSet());

        InterviewRequestDto dto = mapper.toDto(interviewRequest);
        dto.setSlots(slots);

        return dto;
    }

}