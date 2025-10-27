package net.microgoose.mocknet.interview.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.interview.dto.grade.CreateGradeRequest;
import net.microgoose.mocknet.interview.model.Grade;
import net.microgoose.mocknet.interview.service.GradeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    public Grade createGrade(@RequestBody @Valid CreateGradeRequest request) {
        return gradeService.save(request);
    }

    @GetMapping
    public List<Grade> getAllGrades() {
        return gradeService.getAll();
    }
}