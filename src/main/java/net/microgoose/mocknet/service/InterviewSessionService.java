package net.microgoose.mocknet.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.dto.CreateSessionRequest;
import net.microgoose.mocknet.dto.InterviewSessionDto;
import net.microgoose.mocknet.exception.ValidationException;
import net.microgoose.mocknet.mapper.InterviewSessionMapper;
import net.microgoose.mocknet.model.InterviewSession;
import net.microgoose.mocknet.model.SlotBooking;
import net.microgoose.mocknet.repository.InterviewSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterviewSessionService {

    private final InterviewSessionRepository repository;
    private final InterviewSessionMapper mapper;
    private final SlotBookingService slotBookingService;

    @Transactional
    public InterviewSessionDto createSession(CreateSessionRequest request) {
        // TODO create session by booking approve
        if (!slotBookingService.existById(request.getSlotBookingId()))
            throw new ValidationException("Бронирование слота не существует");

        // Генерация ссылки на видеозвонок (заглушка)
        String sessionLink = "https://www.youtube.com/watch?v=dQw4w9WgXcQ&list=RDdQw4w9WgXcQ&start_radio=1";

        SlotBooking slotBooking = SlotBooking.builder()
            .id(request.getSlotBookingId())
            .build();

        InterviewSession session = InterviewSession.builder()
            .slotBooking(slotBooking)
            .sessionLink(sessionLink)
            .createdAt(Instant.now())
            .build();

        return mapper.toDto(repository.save(session));
    }

    public List<InterviewSessionDto> getAllSessions() {
        return mapper.toDto(repository.findAll());
    }

    public InterviewSessionDto getSessionBySlotBooking(UUID slotBookingId) {
        return mapper.toDto(repository.findBySlotBookingId(slotBookingId)
            .orElseThrow(() -> new ValidationException("Сессия не найдена для slotBookingId: " + slotBookingId)));
    }
}