package net.microgoose.mocknet.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.dto.CreateProgrammingLanguageRequest;
import net.microgoose.mocknet.exception.NotFoundException;
import net.microgoose.mocknet.exception.ValidationException;
import net.microgoose.mocknet.model.ProgrammingLanguage;
import net.microgoose.mocknet.repository.ProgrammingLanguageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProgrammingLanguageService {

    private final ProgrammingLanguageRepository repository;

    public boolean existById(UUID id) {
        return repository.existsById(id);
    }

    public List<ProgrammingLanguage> getAllLanguages() {
        return repository.findAll();
    }

    public ProgrammingLanguage getLanguageById(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Язык программирования не найден: " + id));
    }

    @Transactional
    public ProgrammingLanguage createLanguage(CreateProgrammingLanguageRequest request) {
        if (!StringUtils.hasText(request.getName()))
            throw new ValidationException("Название языка программирования не может быть пустым");
        if (repository.existsByName(request.getName()))
            throw new ValidationException("Язык программирования уже существует");

        return repository.save(ProgrammingLanguage.builder().name(request.getName()).build());
    }

}