package net.microgoose.mocknet.service;

import net.microgoose.mocknet.dto.CreateInterviewSlotRequest;
import net.microgoose.mocknet.factory.TestDataFactoryExtension;
import net.microgoose.mocknet.factory.TestFactory;
import net.microgoose.mocknet.factory.dto.CreateInterviewSlotRequestFactory;
import net.microgoose.mocknet.factory.model.InterviewSlotFactory;
import net.microgoose.mocknet.mapper.InterviewSlotMapper;
import net.microgoose.mocknet.model.InterviewSlot;
import net.microgoose.mocknet.repository.InterviewSlotRepository;
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
class InterviewSlotServiceTest {

    @TestFactory
    private InterviewSlotFactory interviewSlotFactory;
    @TestFactory
    private CreateInterviewSlotRequestFactory createInterviewSlotFactory;

    @Mock
    private InterviewSlotRepository repository;

    @Mock
    private InterviewSlotMapper mapper;

    @Mock
    private InterviewRequestService interviewRequestService;

    @InjectMocks
    private InterviewSlotService service;

    @Test
    void getAllSessions() {
        InterviewSlot request = interviewSlotFactory.createPersisted();
        List<InterviewSlot> requests = List.of(request);
        when(repository.findAll()).thenReturn(requests);

        List<InterviewSlot> result = service.getAllSlots();
        verify(repository, times(1)).findAll();
        assertTrue(result.containsAll(requests));
    }

    @Test
    void getSlotById_shouldReturnRequest() {
        UUID uuid = UUID.randomUUID();
        InterviewSlot request = interviewSlotFactory.createPersisted();
        when(repository.findById(uuid)).thenReturn(Optional.of(request));

        InterviewSlot result = service.getSlotById(uuid);
        verify(repository, times(1)).findById(uuid);
        assertEquals(result, request);
    }

    @Test
    void createSlot_shouldCreateSlot() {
        CreateInterviewSlotRequest createInterviewSlotRequest = createInterviewSlotFactory.createNew();
        InterviewSlot interviewSlot = interviewSlotFactory.createPersisted();
        when(interviewRequestService.existById(any())).thenReturn(true);
        when(mapper.fromDto(createInterviewSlotRequest)).thenReturn(interviewSlot);
        when(repository.save(any())).thenReturn(interviewSlot);

        InterviewSlot result = service.createSlot(createInterviewSlotRequest);
        verify(repository, times(1)).save(interviewSlot);
        assertEquals(interviewSlot, result);
    }

    @Test
    void bookSlot_shouldBookSlot() {
        InterviewSlot interviewSlot = interviewSlotFactory.createPersisted();
        interviewSlot.setIsBooked(false);
        UUID interviewerId = UUID.randomUUID();
        UUID slotId = UUID.randomUUID();
        when(repository.findById(slotId)).thenReturn(Optional.of(interviewSlot));
        when(repository.save(any())).thenReturn(interviewSlot);

        InterviewSlot result = service.bookSlot(interviewerId, slotId);
        verify(repository, times(1)).save(interviewSlot);
        assertTrue(result.getIsBooked());
    }

}