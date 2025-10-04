package net.microgoose.mocknet.interview.controller;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.interview.dto.CreateProgrammingLanguageRequest;
import net.microgoose.mocknet.interview.model.ProgrammingLanguage;
import net.microgoose.mocknet.interview.service.ProgrammingLanguageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/programming-languages")
@RequiredArgsConstructor
public class ProgrammingLanguageController {

    private final ProgrammingLanguageService service;

    @GetMapping
    public List<ProgrammingLanguage> getAllLanguages() {
        return service.getAllLanguages();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProgrammingLanguage createLanguage(@RequestBody CreateProgrammingLanguageRequest language) {
        return service.createLanguage(language);
    }

    @GetMapping("/{id}")
    public ProgrammingLanguage getLanguage(@PathVariable UUID id) {
        return service.getLanguageById(id);
    }
}
