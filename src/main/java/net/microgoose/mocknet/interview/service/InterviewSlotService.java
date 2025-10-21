package net.microgoose.mocknet.interview.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.exception.NotFoundException;
import net.microgoose.mocknet.app.exception.ValidationException;
import net.microgoose.mocknet.interview.dto.BookInterviewSlotRequest;
import net.microgoose.mocknet.interview.dto.InterviewSlotDto;
import net.microgoose.mocknet.interview.mapper.InterviewSlotMapper;
import net.microgoose.mocknet.interview.model.ConfirmationStatus;
import net.microgoose.mocknet.interview.model.InterviewRequest;
import net.microgoose.mocknet.interview.model.InterviewSlot;
import net.microgoose.mocknet.interview.model.InterviewUser;
import net.microgoose.mocknet.interview.repository.InterviewSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

import static net.microgoose.mocknet.interview.config.MessageDictionary.SLOT_ALREADY_TAKEN;
import static net.microgoose.mocknet.interview.config.MessageDictionary.SLOT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class InterviewSlotService {

    private final InterviewSlotRepository repository;
    private final InterviewSlotMapper mapper;
    private final EntityManager em;

    public InterviewSlotDto save(InterviewRequest request, Instant startTime) {
        if (repository.existsByRequestAndStartTime(request, startTime))
            throw new ValidationException(SLOT_ALREADY_TAKEN);

        return mapper.toDto(repository.save(InterviewSlot.builder()
            .request(request)
            .status(ConfirmationStatus.PENDING)
            .startTime(startTime)
            .bookers(new HashSet<>())
            .build()));
    }

    public InterviewSlotDto save(InterviewRequest request, OffsetDateTime startTime) {
        return save(request, startTime.toInstant());
    }

    @Transactional
    public InterviewSlotDto bookSlot(UUID userId, BookInterviewSlotRequest request) {
        InterviewSlot slot = repository.findById(request.getSlotUuid())
            .orElseThrow(() -> new NotFoundException(SLOT_NOT_FOUND));

        boolean isAlreadyTaken = slot.getBookers().stream()
            .anyMatch(iu -> Objects.equals(userId, iu.getId()));

        if (isAlreadyTaken && ConfirmationStatus.CONFIRMED.equals(slot.getStatus()))
            throw new ValidationException(SLOT_ALREADY_TAKEN);

        slot.getBookers().add(em.getReference(InterviewUser.class, userId));

        return mapper.toDto(slot);
    }

}