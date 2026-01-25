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

    private final InterviewSlotRepository slotRepository;
    private final InterviewSlotMapper slotMapper;

    private final InterviewUserRepository userService;
    private final InterviewUserMapper userMapper;
    private final EntityManager em;

    public InterviewSlot getById(UUID id) {
        return slotRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(SLOT_NOT_FOUND));
    }

    public Page<InterviewRequestSlotDto> viewByRequestId(UUID interviewRequestId, Pageable pageable) {
        Page<InterviewSlot> slots = slotRepository.findByRequestIdWithBooker(interviewRequestId, pageable);
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

    public List<InterviewSlot> getByRequestId(UUID requestId) {
        return slotRepository.findByRequest_Id(requestId);
    }

    @Transactional
    public List<InterviewSlotDto> createSlots(UUID bookerId, InterviewRequest interviewRequest, CreateInterviewSlotRequest request) {
        if (!Objects.equals(interviewRequest.getStatus(), InterviewRequestStatus.NEW)) {
            throw new ValidationException(SLOT_INTERVIEW_STATUS_CONFLICT);
        }

        UUID interviewRequestUuid = interviewRequest.getId();
        UUID creatorId = userService.findUserIdByRequestId(interviewRequestUuid);
        List<InterviewSlot> slots = new ArrayList<>();

        for (OffsetDateTime startTimeWithOffset : request.getStartTimes()) {
            Instant startTime = startTimeWithOffset.toInstant();

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
    public InterviewSlot updateStatus(InterviewSlot slot, ConfirmationStatus status) {
        slot.setStatus(status);
        return slotRepository.save(slot);
    }

    @Transactional
    public List<InterviewSlot> updateStatus(List<InterviewSlot> slots, ConfirmationStatus status) {
        slots.forEach(s -> s.setStatus(status));
        return slotRepository.saveAll(slots);
    }

}