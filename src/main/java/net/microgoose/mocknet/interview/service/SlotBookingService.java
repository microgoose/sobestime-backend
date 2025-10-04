package net.microgoose.mocknet.interview.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.exception.NotFoundException;
import net.microgoose.mocknet.interview.dto.CreateSlotBookingRequest;
import net.microgoose.mocknet.interview.mapper.SlotBookingMapper;
import net.microgoose.mocknet.interview.model.SlotBooking;
import net.microgoose.mocknet.interview.repository.SlotBookingRepository;
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

    public boolean existById(UUID id) {
        return repository.existsById(id);
    }

    public List<SlotBooking> getBookingsByInterviewer(UUID interviewerId) {
        return repository.findByInterviewerId(interviewerId);
    }

    public SlotBooking getBookingBySlot(UUID slotId) {
        return repository.findBySlotId(slotId)
            .orElseThrow(() -> new NotFoundException("Бронирование не найдено для слота: " + slotId));
    }

    @Transactional
    public SlotBooking createSlotBooking(CreateSlotBookingRequest request) {
        interviewSlotService.bookSlot(request.getInterviewerId(), request.getSlotId());
        return repository.save(mapper.fromDto(request));
    }
}