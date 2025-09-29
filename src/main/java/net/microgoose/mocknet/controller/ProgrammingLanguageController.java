package net.microgoose.mocknet.controller;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.dto.CreateProgrammingLanguageRequest;
import net.microgoose.mocknet.model.ProgrammingLanguage;
import net.microgoose.mocknet.service.ProgrammingLanguageService;
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
