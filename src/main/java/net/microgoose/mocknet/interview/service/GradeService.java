package net.microgoose.mocknet.interview.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.exception.ValidationException;
import net.microgoose.mocknet.interview.dto.grade.CreateGradeRequest;
import net.microgoose.mocknet.interview.model.Grade;
import net.microgoose.mocknet.interview.repository.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.microgoose.mocknet.interview.config.MessageDictionary.GRADE_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository repository;

    public List<Grade> getAll() {
        return repository.findAll();
    }

    public Grade save(CreateGradeRequest request) {
        if (repository.existsByName(request.getName()))
            throw new ValidationException(GRADE_ALREADY_EXIST);

        return repository.save(Grade.builder()
            .name(request.getName())
            .build());
    }

}