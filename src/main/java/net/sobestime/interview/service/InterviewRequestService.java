package net.sobestime.interview.service;

import lombok.RequiredArgsConstructor;
import net.sobestime.app.exception.IllegalActionException;
import net.sobestime.app.exception.NotFoundException;
import net.sobestime.app.exception.ValidationException;
import net.sobestime.interview.dto.interview_request.CreateInterviewRequest;
import net.sobestime.interview.dto.interview_request.InterviewRequestDto;
import net.sobestime.interview.dto.interview_request.UpdateInterviewRequest;
import net.sobestime.interview.mapper.InterviewRequestMapper;
import net.sobestime.interview.model.*;
import net.sobestime.interview.repository.InterviewRequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static net.sobestime.interview.config.MessageDictionary.*;

@Service
@RequiredArgsConstructor
public class InterviewRequestService {

    private final InterviewRequestRepository requestRepository;
    private final InterviewRequestMapper mapper;

    private final InterviewUserService userService;
    private final InterviewRoleService roleService;
    private final SkillService skillService;
    private final GradeService gradeService;

    // READ

    public Page<InterviewRequestDto> getAll(Pageable pageable) {
        return mapper.toDto(requestRepository.findAll(pageable));
    }

    public InterviewRequest getById(UUID requestId) {
        return requestRepository.findById(requestId)
            .orElseThrow(() -> new NotFoundException(INTERVIEW_REQUEST_NOT_FOUND));
    }

    public InterviewRequest getBySlotId(UUID slotId) {
        return requestRepository.findBySlotId(slotId)
            .orElseThrow(() -> new NotFoundException(INTERVIEW_REQUEST_NOT_FOUND));
    }

    public InterviewRequestDto viewById(UUID requestId) {
        return mapper.toDto(getById(requestId));
    }

    // WRITE (request aggregate only)

    @Transactional
    public InterviewRequest save(InterviewRequest request) {
        return requestRepository.save(request);
    }

    @Transactional
    public InterviewRequest updateStatus(InterviewRequest request, InterviewRequestStatus status) {
        request.setStatus(status);
        return requestRepository.save(request);
    }

    @Transactional
    public InterviewRequestDto createRequest(UUID creatorId, CreateInterviewRequest request) {
        InterviewUser user = userService.getById(creatorId);

        InterviewRole role = roleService.getById(request.getRoleUuid());
        List<Skill> skills = skillService.getByIdIn(request.getSkillUuids());
        List<Grade> grades = gradeService.getByIdIn(request.getGradeUuids());

        // TODO: Валидировать title/description (длина, запрещённые символы и т.п.)

        InterviewRequest entity = InterviewRequest.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .status(InterviewRequestStatus.NEW)
            .creator(user)
            .role(role)
            .grades(new HashSet<>(grades))
            .skills(new HashSet<>(skills))
            .slots(new HashSet<>())
            .build();

        return mapper.toDto(save(entity));
    }

    @Transactional
    public InterviewRequestDto updateRequest(UUID creatorId, UUID requestId, UpdateInterviewRequest update) {
        InterviewUser user = userService.getById(creatorId);
        InterviewRequest request = getById(requestId);

        assertOwner(request, user, INTERVIEW_REQUEST_UPDATE_OWNER_CONFLICT);
        assertEditable(request);

        InterviewRole role = roleService.getById(update.getRoleUuid());
        List<Skill> skills = skillService.getByIdIn(update.getSkillUuids());
        List<Grade> grades = gradeService.getByIdIn(update.getGradeUuids());

        request.setTitle(update.getTitle());
        request.setDescription(update.getDescription());
        request.setRole(role);
        request.setGrades(new HashSet<>(grades));
        request.setSkills(new HashSet<>(skills));

        return mapper.toDto(requestRepository.save(request));
    }

    public void validateCancelableByCreator(UUID creatorId, InterviewRequest request) {
        InterviewUser user = userService.getById(creatorId);

        assertOwner(request, user, INTERVIEW_REQUEST_CANCEL_OWNER_CONFLICT);

        // У вас запрещено отменять только ENDED — оставляем как есть.
        if (request.getStatus().equals(InterviewRequestStatus.ENDED)) {
            throw new ValidationException(String.format(
                INTERVIEW_REQUEST_CANCEL_STATUS_CONFLICT, request.getStatus().getDescription()
            ));
        }
    }

    // --- helpers ---

    private void assertOwner(InterviewRequest request, InterviewUser user, String reason) {
        if (!Objects.equals(request.getCreator().getId(), user.getId())) {
            throw new ValidationException(reason);
        }
    }

    private void assertEditable(InterviewRequest request) {
        if (!request.getStatus().equals(InterviewRequestStatus.NEW)) {
            throw new IllegalActionException(String.format(
                INTERVIEW_REQUEST_UPDATE_STATUS_CONFLICT, request.getStatus().getDescription()
            ));
        }
    }
}
