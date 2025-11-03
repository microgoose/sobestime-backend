package net.sobestime.interview.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import net.sobestime.app.exception.NotFoundException;
import net.sobestime.app.exception.ValidationException;
import net.sobestime.interview.dto.interview_slot.CreateInterviewSlotRequest;
import net.sobestime.interview.dto.interview_slot.InterviewRequestSlotDto;
import net.sobestime.interview.dto.interview_slot.InterviewSlotDto;
import net.sobestime.interview.dto.interview_slot.SimpleInterviewSlotDto;
import net.sobestime.interview.dto.interview_user.InterviewUserDto;
import net.sobestime.interview.mapper.InterviewSlotMapper;
import net.sobestime.interview.mapper.InterviewUserMapper;
import net.sobestime.interview.model.*;
import net.sobestime.interview.repository.InterviewRequestRepository;
import net.sobestime.interview.repository.InterviewSlotRepository;
import net.sobestime.interview.repository.InterviewUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;

import static net.sobestime.interview.config.MessageDictionary.*;

@Service
@RequiredArgsConstructor
public class InterviewSlotService {

    private final ScheduledInterviewService interviewService;
    private final InterviewSlotRepository slotRepository;
    private final InterviewRequestRepository requestRepository;
    private final InterviewUserRepository userRepository;
    private final InterviewSlotMapper slotMapper;
    private final InterviewUserMapper userMapper;
    private final EntityManager em;

    public Page<InterviewRequestSlotDto> findSlotsByRequest(UUID interviewRequest, Pageable pageable) {
        Page<InterviewSlot> slots = slotRepository.findByRequestIdWithBooker(interviewRequest, pageable);
        Map<UUID, InterviewRequestSlotDto> slotDtos = new HashMap<>();

        for (InterviewSlot slot : slots) {
            InterviewUser user = slot.getBooker();

            if (!slotDtos.containsKey(user.getId())) {
                InterviewUserDto interviewUserDto = userMapper.toDto(user);

                slotDtos.put(user.getId(), InterviewRequestSlotDto.builder()
                    .user(interviewUserDto)
                    .slots(new HashSet<>())
                    .build());
            }

            SimpleInterviewSlotDto simpleSlot = slotMapper.toSimpleDto(slot);
            InterviewRequestSlotDto dto = slotDtos.get(user.getId());
            dto.getSlots().add(simpleSlot);
        }

        return new PageImpl<>(
            new ArrayList<>(slotDtos.values()),
            pageable,
            slots.getTotalElements()
        );
    }

    @Transactional
    public List<InterviewSlotDto> createSlots(UUID bookerId, CreateInterviewSlotRequest request) {
        InterviewRequest interviewRequest = requestRepository.findById(request.getInterviewRequestUuid())
            .orElseThrow(() -> new NotFoundException(INTERVIEW_REQUEST_NOT_FOUND));

        if (!Objects.equals(interviewRequest.getStatus(), InterviewRequestStatus.NEW))
            throw new ValidationException(SLOT_INTERVIEW_STATUS_CONFLICT);

        UUID interviewRequestUuid = request.getInterviewRequestUuid();
        UUID creatorId = userRepository.findUserIdByRequestId(interviewRequestUuid);
        List<InterviewSlot> slots = new ArrayList<>();

        for (OffsetDateTime startTimeWithOffset : request.getStartTimes()) {
            Instant startTime = startTimeWithOffset.toInstant();

            // todo not in past

            if (slotRepository.existsByRequest_IdAndBooker_IdAndStartTime(interviewRequestUuid, bookerId, startTime))
                throw new ValidationException(SLOT_ALREADY_TAKEN_BY_YOU);

            boolean isOwnerBooking = Objects.equals(creatorId, bookerId);

            if (isOwnerBooking)
                throw new ValidationException(SLOT_BOOKER_CREATOR_CONFLICT);

            slots.add(InterviewSlot.builder()
                .status(ConfirmationStatus.PENDING)
                .startTime(startTime)
                .request(interviewRequest)
                .booker(em.getReference(InterviewUser.class, bookerId))
                .build());
        }

        return slotMapper.toDto(slotRepository.saveAll(slots));
    }

    @Transactional
    public InterviewSlotDto approveSlot(UUID creatorId, UUID slotId) {
        InterviewRequest request = requestRepository.findBySlotId(slotId)
            .orElseThrow(() -> new NotFoundException(INTERVIEW_REQUEST_NOT_FOUND));
        InterviewSlot slot = slotRepository.findById(slotId)
            .orElseThrow(() -> new NotFoundException(SLOT_NOT_FOUND));

        if (!request.getCreator().getId().equals(creatorId))
            throw new ValidationException(INTERVIEW_REQUEST_APPROVE_CONFLICT);
        if (slot.getStatus().equals(ConfirmationStatus.CONFIRMED))
            throw new ValidationException(SLOT_ALREADY_APPROVED_CONFLICT);

        request.setStatus(InterviewRequestStatus.PLANNED);
        slot.setStatus(ConfirmationStatus.CONFIRMED);

        interviewService.createScheduledInterview(request, slot);
        requestRepository.save(request);
        return slotMapper.toDto(slotRepository.save(slot));
    }

    @Transactional
    public InterviewSlotDto rejectSlot(UUID creatorId, UUID slotId) {
        InterviewRequest request = requestRepository.findBySlotId(slotId)
            .orElseThrow(() -> new NotFoundException(INTERVIEW_REQUEST_NOT_FOUND));
        InterviewSlot slot = slotRepository.findById(slotId)
            .orElseThrow(() -> new NotFoundException(SLOT_NOT_FOUND));

        if (!request.getCreator().getId().equals(creatorId))
            throw new ValidationException(INTERVIEW_REQUEST_REJECT_CONFLICT);
        if (slot.getStatus().equals(ConfirmationStatus.REJECTED))
            throw new ValidationException(SLOT_ALREADY_REJECTED_CONFLICT);

        request.setStatus(InterviewRequestStatus.NEW);
        slot.setStatus(ConfirmationStatus.REJECTED);

        requestRepository.save(request);
        return slotMapper.toDto(slotRepository.save(slot));
    }

    // TODO cancel book

}