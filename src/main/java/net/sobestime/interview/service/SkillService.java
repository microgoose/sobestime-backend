package net.sobestime.interview.service;

import lombok.RequiredArgsConstructor;
import net.sobestime.app.exception.ValidationException;
import net.sobestime.interview.dto.skill.CreateSkillRequest;
import net.sobestime.interview.model.Skill;
import net.sobestime.interview.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static net.sobestime.interview.config.MessageDictionary.SKILLS_NOT_FOUND;
import static net.sobestime.interview.config.MessageDictionary.SKILL_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public List<Skill> getAll() {
        return skillRepository.findAll();
    }

    public List<Skill> getByIdIn(Set<UUID> skillUUIDs) {
        List<Skill> skills = skillRepository.findAllById(skillUUIDs);

        if (skills.size() != skillUUIDs.size())
            throw new ValidationException(SKILLS_NOT_FOUND);

        return skills;
    }

    public Skill save(CreateSkillRequest request) {
        if (skillRepository.existsByName(request.getName()))
            throw new ValidationException(SKILL_ALREADY_EXIST);

        return skillRepository.save(Skill.builder()
            .name(request.getName())
            .build());
    }

}