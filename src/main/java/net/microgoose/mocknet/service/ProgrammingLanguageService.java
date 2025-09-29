package net.microgoose.mocknet.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.dto.CreateProgrammingLanguageRequest;
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

    @Transactional
    public ProgrammingLanguage createLanguage(CreateProgrammingLanguageRequest request) {
        if (repository.existsByName(request.getName()))
            throw new ValidationException("Язык программирования уже существует");
        if (!StringUtils.hasText(request.getName()))
            throw new ValidationException("Название языка программирования не может быть пустым");

        return repository.save(ProgrammingLanguage.builder().name(request.getName()).build());
    }

    public List<ProgrammingLanguage> getAllLanguages() {
        return repository.findAll();
    }

    public boolean existById(UUID id) {
        return repository.existsById(id);
    }

    public ProgrammingLanguage getLanguageById(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new ValidationException("Язык программирования не найден: " + id));
    }

}