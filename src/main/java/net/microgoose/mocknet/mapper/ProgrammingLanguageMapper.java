package net.microgoose.mocknet.mapper;

import net.microgoose.mocknet.model.ProgrammingLanguage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProgrammingLanguageMapper {

    public ProgrammingLanguage map(UUID uuid) {
        return ProgrammingLanguage.builder()
            .id(uuid)
            .build();
    }
}
