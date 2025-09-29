package net.microgoose.mocknet.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.dto.CreateInterviewSlotRequest;
import net.microgoose.mocknet.dto.InterviewSlotDto;
import net.microgoose.mocknet.exception.ValidationException;
import net.microgoose.mocknet.mapper.InterviewSlotMapper;
import net.microgoose.mocknet.repository.InterviewSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterviewSlotService {

    private final InterviewSlotRepository repository;
    private final InterviewSlotMapper mapper;
    private final InterviewRequestService interviewRequestService;

    @Transactional
    public InterviewSlotDto createSlot(CreateInterviewSlotRequest request) {
        if (!interviewRequestService.existById(request.getInterviewRequestId()))
            throw new ValidationException("Запрос на интервью не существует");

        if (request.getEndTime().isBefore(OffsetDateTime.now()))
            throw new ValidationException("Время окончания не может быть раньше текущего времени");
        if (request.getStartTime().isEqual(request.getEndTime()))
            throw new ValidationException("Время начала не может совпадать с временем окончания");
        if (request.getStartTime().isAfter(request.getEndTime()))
            throw new ValidationException("Время начала не может быть позже времени окончания");

        return mapper.toDto(repository.save(mapper.fromDto(request)));
    }

    @Transactional
    public InterviewSlotDto bookSlot(UUID slotId) {
        InterviewSlotDto slot = getSlotById(slotId);

        if (slot.isBooked()) {
            throw new IllegalStateException("Слот уже забронирован: " + slotId);
        }

        slot.setBooked(true);
        return mapper.toDto(repository.save(mapper.fromDto(slot)));
    }

    public List<InterviewSlotDto> getAllSlots() {
        return mapper.toDto(repository.findAll());
    }

    public boolean existById(UUID id) {
        return repository.existsById(id);
    }

    public List<InterviewSlotDto> getAvailableSlotsAfter(OffsetDateTime dateTime) {
        return mapper.toDto(repository.findByIsBookedFalseAndStartTimeAfter(dateTime.toInstant()));
    }

    public InterviewSlotDto getSlotById(UUID id) {
        return mapper.toDto(repository.findById(id)
            .orElseThrow(() -> new ValidationException("Слот для интервью не найден: " + id)));
    }
}