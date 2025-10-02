package net.microgoose.mocknet.service;

import net.microgoose.mocknet.dto.CreateInterviewRequest;
import net.microgoose.mocknet.factory.TestDataFactoryExtension;
import net.microgoose.mocknet.factory.TestFactory;
import net.microgoose.mocknet.factory.dto.CreateInterviewRequestFactory;
import net.microgoose.mocknet.factory.model.InterviewRequestFactory;
import net.microgoose.mocknet.mapper.InterviewRequestMapper;
import net.microgoose.mocknet.model.InterviewRequest;
import net.microgoose.mocknet.repository.InterviewRequestRepository;
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
class InterviewRequestServiceTest {

    @TestFactory
    private InterviewRequestFactory interviewRequestFactory;
    @TestFactory
    private CreateInterviewRequestFactory createInterviewRequestFactory;

    @Mock
    private UserService userService;
    @Mock
    private ProgrammingLanguageService languageService;
    @Mock
    private InterviewRequestRepository repository;
    @Mock
    private InterviewRequestMapper mapper;
    @InjectMocks
    private InterviewRequestService service;

    @Test
    void existById_shouldReturnTrue() {
        UUID uuid = UUID.randomUUID();
        when(repository.existsById(uuid)).thenReturn(true);
        assertTrue(service.existById(uuid));
        verify(repository, times(1)).existsById(uuid);
    }

    @Test
    void getAllRequests_shouldReturnAllRequests() {
        InterviewRequest request = interviewRequestFactory.createPersisted();
        List<InterviewRequest> requests = List.of(request);
        when(repository.findAll()).thenReturn(requests);

        List<InterviewRequest> result = service.getAllRequests();
        verify(repository, times(1)).findAll();
        assertTrue(result.containsAll(requests));
    }

    @Test
    void getRequestById_shouldReturnRequest() {
        UUID uuid = UUID.randomUUID();
        InterviewRequest request = interviewRequestFactory.createPersisted();
        when(repository.findById(uuid)).thenReturn(Optional.of(request));

        InterviewRequest result = service.getRequestById(uuid);
        verify(repository, times(1)).findById(uuid);
        assertEquals(result, request);
    }

    @Test
    void createRequest_shouldCreateRequest() {
        CreateInterviewRequest request = createInterviewRequestFactory.createNew();
        InterviewRequest resultData = interviewRequestFactory.createPersisted();
        when(userService.existById(request.getCreatorId())).thenReturn(true);
        when(languageService.existById(request.getProgrammingLanguageId())).thenReturn(true);
        when(mapper.fromDto(any())).thenReturn(resultData);
        when(repository.save(any())).thenReturn(resultData);

        InterviewRequest result = service.createRequest(request);
        verify(repository, times(1)).save(resultData);
        assertEquals(resultData, result);
    }
}