package net.microgoose.mocknet.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.dto.CreateInterviewSlotRequest;
import net.microgoose.mocknet.exception.IllegalActionException;
import net.microgoose.mocknet.exception.NotFoundException;
import net.microgoose.mocknet.exception.ValidationException;
import net.microgoose.mocknet.mapper.InterviewSlotMapper;
import net.microgoose.mocknet.model.InterviewSlot;
import net.microgoose.mocknet.repository.InterviewSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterviewSlotService {

    private final InterviewSlotRepository repository;
    private final InterviewSlotMapper mapper;
    private final InterviewRequestService interviewRequestService;

    public List<InterviewSlot> getAllSlots() {
        return repository.findAll();
    }

    public InterviewSlot getSlotById(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Слот для интервью не найден: " + id));
    }

    @Transactional
    public InterviewSlot createSlot(CreateInterviewSlotRequest request) {
        if (!interviewRequestService.existById(request.getInterviewRequestId()))
            throw new ValidationException("Запрос на интервью не существует");

        if (request.getEndTime().isBefore(OffsetDateTime.now()))
            throw new ValidationException("Время окончания не может быть раньше текущего времени");
        if (request.getStartTime().isEqual(request.getEndTime()))
            throw new ValidationException("Время начала не может совпадать с временем окончания");
        if (request.getStartTime().isAfter(request.getEndTime()))
            throw new ValidationException("Время начала не может быть позже времени окончания");

        return repository.save(mapper.fromDto(request));
    }

    @Transactional
    public InterviewSlot bookSlot(UUID interviewerId, UUID slotId) {
        InterviewSlot slot = getSlotById(slotId);
        UUID creatorId = slot.getInterviewRequest().getCreator().getId();

        if (Objects.equals(interviewerId, creatorId))
            throw new ValidationException("Нельзя забронировать свой же слот");
        if (slot.getIsBooked())
            throw new IllegalActionException("Слот уже забронирован: " + slotId);

        slot.setIsBooked(true);
        return repository.save(slot);
    }
}