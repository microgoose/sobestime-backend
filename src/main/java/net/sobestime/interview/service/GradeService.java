package net.sobestime.interview.service;

import lombok.RequiredArgsConstructor;
import net.sobestime.app.exception.ValidationException;
import net.sobestime.interview.dto.grade.CreateGradeRequest;
import net.sobestime.interview.model.Grade;
import net.sobestime.interview.repository.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static net.sobestime.interview.config.MessageDictionary.GRADES_NOT_FOUND;
import static net.sobestime.interview.config.MessageDictionary.GRADE_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;

    public List<Grade> getAll() {
        return gradeRepository.findAll();
    }

    public List<Grade> getByIdIn(Set<UUID> gradeIds) {
        List<Grade> grades = gradeRepository.findAllById(gradeIds);

        if (grades.size() != gradeIds.size())
            throw new ValidationException(GRADES_NOT_FOUND);

        return grades;
    }

    public Grade save(CreateGradeRequest request) {
        if (gradeRepository.existsByName(request.getName()))
            throw new ValidationException(GRADE_ALREADY_EXIST);

        return gradeRepository.save(Grade.builder()
            .name(request.getName())
            .build());
    }

}