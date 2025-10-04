package net.microgoose.mocknet.interview;

import net.microgoose.mocknet.factory.TestDataFactoryExtension;
import net.microgoose.mocknet.factory.TestFactory;
import net.microgoose.mocknet.factory.model.InterviewSessionFactory;
import net.microgoose.mocknet.interview.model.InterviewSession;
import net.microgoose.mocknet.interview.repository.InterviewSessionRepository;
import net.microgoose.mocknet.interview.service.InterviewSessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, TestDataFactoryExtension.class})
class InterviewSessionServiceTest {

    @TestFactory
    private InterviewSessionFactory interviewSessionFactory;

    @Mock
    private InterviewSessionRepository repository;
    @InjectMocks
    private InterviewSessionService service;

    @Test
    void getAllSessions() {
        InterviewSession request = interviewSessionFactory.createPersisted();
        List<InterviewSession> requests = List.of(request);
        when(repository.findAll()).thenReturn(requests);

        List<InterviewSession> result = service.getAllSessions();
        verify(repository, times(1)).findAll();
        assertTrue(result.containsAll(requests));
    }
}