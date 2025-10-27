package net.microgoose.mocknet.interview.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.interview.dto.skill.CreateSkillRequest;
import net.microgoose.mocknet.interview.model.Skill;
import net.microgoose.mocknet.interview.service.SkillService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public Skill createSkill(@RequestBody @Valid CreateSkillRequest request) {
        return skillService.save(request);
    }

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillService.getAll();
    }
}