package net.microgoose.mocknet.interview.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.exception.ValidationException;
import net.microgoose.mocknet.interview.dto.CreateSkillRequest;
import net.microgoose.mocknet.interview.model.Skill;
import net.microgoose.mocknet.interview.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.microgoose.mocknet.interview.config.MessageDictionary.SKILL_ALREADY_EXIST;

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