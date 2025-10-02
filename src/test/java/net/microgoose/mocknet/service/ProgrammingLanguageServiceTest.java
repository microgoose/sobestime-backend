package net.microgoose.mocknet.service;

import net.microgoose.mocknet.dto.CreateProgrammingLanguageRequest;
import net.microgoose.mocknet.factory.TestDataFactoryExtension;
import net.microgoose.mocknet.factory.TestFactory;
import net.microgoose.mocknet.factory.dto.CreateProgrammingLanguageRequestFactory;
import net.microgoose.mocknet.factory.model.ProgrammingLanguageFactory;
import net.microgoose.mocknet.model.ProgrammingLanguage;
import net.microgoose.mocknet.repository.ProgrammingLanguageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, TestDataFactoryExtension.class})
class ProgrammingLanguageServiceTest {

    @TestFactory
    private ProgrammingLanguageFactory programmingLanguageFactory;
    @TestFactory
    private CreateProgrammingLanguageRequestFactory createProgrammingLanguageFactory;

    @Mock
    private ProgrammingLanguageRepository repository;

    @InjectMocks
    private ProgrammingLanguageService service;

    @Test
    void existById_returnsTrue() {
        UUID uuid = UUID.randomUUID();
        when(repository.existsById(uuid)).thenReturn(true);
        assertTrue(service.existById(uuid));
        verify(repository, times(1)).existsById(uuid);
    }

    @Test
    void getAllSessions() {
        ProgrammingLanguage request = programmingLanguageFactory.createPersisted();
        List<ProgrammingLanguage> requests = List.of(request);
        when(repository.findAll()).thenReturn(requests);

        List<ProgrammingLanguage> result = service.getAllLanguages();
        verify(repository, times(1)).findAll();
        assertTrue(result.containsAll(requests));
    }

    @Test
    void getLanguageById_returnsLanguage() {
        UUID uuid = UUID.randomUUID();
        ProgrammingLanguage data = programmingLanguageFactory.createPersisted();
        when(repository.findById(uuid)).thenReturn(Optional.of(data));

        ProgrammingLanguage result = service.getLanguageById(uuid);
        verify(repository, times(1)).findById(uuid);
        assertEquals(data, result);
    }

    @Test
    void createLanguage_shouldCreateNewLanguage() {
        CreateProgrammingLanguageRequest request = createProgrammingLanguageFactory.createNew();
        ProgrammingLanguage data = programmingLanguageFactory.createPersisted();
        when(repository.existsByName(request.getName())).thenReturn(false);
        when(repository.save(any())).thenReturn(data);

        ProgrammingLanguage result = service.createLanguage(request);
        verify(repository, times(1)).existsByName(request.getName());
        verify(repository, times(1)).save(any());
        assertEquals(data, result);
    }
}