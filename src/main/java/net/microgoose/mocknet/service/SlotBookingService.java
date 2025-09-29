package net.microgoose.mocknet.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.dto.CreateSlotBookingRequest;
import net.microgoose.mocknet.dto.SlotBookingDto;
import net.microgoose.mocknet.exception.ValidationException;
import net.microgoose.mocknet.mapper.SlotBookingMapper;
import net.microgoose.mocknet.repository.SlotBookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlotBookingService {

    private final SlotBookingRepository repository;
    private final InterviewSlotService interviewSlotService;
    private final SlotBookingMapper mapper;

    @Transactional
    public SlotBookingDto createSlotBooking(CreateSlotBookingRequest request) {
        // TODO themself booking
        interviewSlotService.bookSlot(request.getSlotId());
        return mapper.toDto(repository.save(mapper.fromDto(request)));
    }

    public boolean existById(UUID id) {
        return repository.existsById(id);
    }

    public List<SlotBookingDto> getBookingsByInterviewer(UUID interviewerId) {
        return mapper.toDto(repository.findByInterviewerId(interviewerId));
    }

    public SlotBookingDto getBookingBySlot(UUID slotId) {
        return mapper.toDto(repository.findBySlotId(slotId)
            .orElseThrow(() -> new ValidationException("Бронирование не найдено для слота: " + slotId)));
    }
}