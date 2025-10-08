package net.microgoose.mocknet.interview.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.exception.NotFoundException;
import net.microgoose.mocknet.app.exception.ValidationException;
import net.microgoose.mocknet.auth.service.AuthUserService;
import net.microgoose.mocknet.interview.dto.CreateInterviewRequest;
import net.microgoose.mocknet.interview.mapper.InterviewRequestMapper;
import net.microgoose.mocknet.interview.model.InterviewRequest;
import net.microgoose.mocknet.interview.repository.InterviewRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterviewRequestService {

    private final InterviewRequestRepository repository;
    private final InterviewRequestMapper mapper;
    private final AuthUserService authUserService; // TODO прямой вызов микросервиса, нежелательно
    private final ProgrammingLanguageService languageService;

    public boolean existById(UUID id) {
        return repository.existsById(id);
    }

    public List<InterviewRequest> getAllRequests() {
        return repository.findAll();
    }

    public InterviewRequest getRequestById(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Запрос на интервью не найден: " + id));
    }

    @Transactional
    public InterviewRequest createRequest(CreateInterviewRequest request) {
        if (!authUserService.existById(request.getCreatorId()))
            throw new ValidationException("Пользователь не существует");
        if (!languageService.existById(request.getProgrammingLanguageId()))
            throw new ValidationException("Язык программирования не существует");

        if (!StringUtils.hasText(request.getTitle()))
            throw new ValidationException("Название не может быть пустым");
        if (request.getTitle().length() > 50)
            throw new ValidationException("Название не может быть длиннее 50 символов");
        if (!StringUtils.hasText(request.getDescription()))
            throw new ValidationException("Описание не может быть пустым");
        if (request.getDescription().length() > 50)
            throw new ValidationException("Описание не может быть длиннее 50 символов");

        return repository.save(mapper.fromDto(request));
    }
}