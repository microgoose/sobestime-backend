// ProgrammingLanguageFactory.java
package net.microgoose.mocknet.factory.model;

import net.microgoose.mocknet.factory.ModelTestFactory;
import net.microgoose.mocknet.model.ProgrammingLanguage;

import java.util.UUID;

public class ProgrammingLanguageFactory implements ModelTestFactory<ProgrammingLanguage> {
    
    @Override
    public ProgrammingLanguage createNew() {
        return ProgrammingLanguage.builder()
            .id(UUID.randomUUID())
            .name("New Language")
            .build();
    }

    @Override
    public ProgrammingLanguage createPersisted() {
        return ProgrammingLanguage.builder()
            .id(UUID.fromString("77345678-1234-1234-1234-123456789abc"))
            .name("Existing Language")
            .build();
    }

    @Override
    public ProgrammingLanguage createValid() {
        return ProgrammingLanguage.builder()
            .id(UUID.randomUUID())
            .name("Java")
            .build();
    }
}

