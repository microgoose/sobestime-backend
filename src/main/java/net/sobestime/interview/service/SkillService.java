package net.sobestime.interview.service;

import lombok.RequiredArgsConstructor;
import net.sobestime.app.exception.ValidationException;
import net.sobestime.interview.dto.skill.CreateSkillRequest;
import net.sobestime.interview.model.Skill;
import net.sobestime.interview.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.sobestime.interview.config.MessageDictionary.SKILL_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository repository;

    public List<Skill> getAll() {
        return repository.findAll();
    }

    public Skill save(CreateSkillRequest request) {
        if (repository.existsByName(request.getName()))
            throw new ValidationException(SKILL_ALREADY_EXIST);

        return repository.save(Skill.builder()
            .name(request.getName())
            .build());
    }

}