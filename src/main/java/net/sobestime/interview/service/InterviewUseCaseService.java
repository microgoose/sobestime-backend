package net.sobestime.interview.service;

import lombok.RequiredArgsConstructor;
import net.sobestime.app.exception.ValidationException;
import net.sobestime.interview.dto.interview_slot.CreateInterviewSlotRequest;
import net.sobestime.interview.dto.interview_slot.InterviewSlotDto;
import net.sobestime.interview.mapper.InterviewSlotMapper;
import net.sobestime.interview.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static net.sobestime.interview.config.MessageDictionary.*;

@Service
@RequiredArgsConstructor
public class InterviewUseCaseService {

    private final InterviewRequestService requestService;
    private final InterviewSlotService slotService;
    private final ScheduledInterviewService scheduledInterviewService;
    private final InterviewSlotMapper slotMapper;

    @Transactional
    public List<InterviewSlotDto> createSlots(UUID bookerId, CreateInterviewSlotRequest request) {
        InterviewRequest interviewRequest = requestService.getById(request.getInterviewRequestUuid());
        return slotService.createSlots(bookerId, interviewRequest, request);
    }

    @Transactional
    public InterviewSlotDto approveSlot(UUID creatorId, UUID slotId) {
        InterviewRequest request = requestService.getBySlotId(slotId);
        InterviewSlot slot = slotService.getById(slotId);

        if (!Objects.equals(request.getCreator().getId(), creatorId)) {
            throw new ValidationException(INTERVIEW_REQUEST_APPROVE_CONFLICT);
        }
        if (slot.getStatus().equals(ConfirmationStatus.CONFIRMED)) {
            throw new ValidationException(SLOT_ALREADY_APPROVED_CONFLICT);
        }

        scheduledInterviewService.createScheduledInterview(request, slot);
        requestService.updateStatus(request, InterviewRequestStatus.PLANNED);
        slotService.updateStatus(slot, ConfirmationStatus.CONFIRMED);

        return slotMapper.toDto(slot);
    }

    @Transactional
    public InterviewSlotDto rejectSlot(UUID creatorId, UUID slotId) {
        InterviewRequest request = requestService.getBySlotId(slotId);
        InterviewSlot slot = slotService.getById(slotId);

        if (!Objects.equals(request.getCreator().getId(), creatorId)) {
            throw new ValidationException(INTERVIEW_REQUEST_REJECT_CONFLICT);
        }
        if (slot.getStatus().equals(ConfirmationStatus.REJECTED)) {
            throw new ValidationException(SLOT_ALREADY_REJECTED_CONFLICT);
        }

        requestService.updateStatus(request, InterviewRequestStatus.NEW);
        slotService.updateStatus(slot, ConfirmationStatus.REJECTED);

        return slotMapper.toDto(slot);
    }

    @Transactional
    public InterviewSlotDto cancelSlot(UUID creatorId, UUID slotId) {
        InterviewRequest request = requestService.getBySlotId(slotId);
        InterviewSlot slot = slotService.getById(slotId);

        if (!Objects.equals(request.getCreator().getId(), creatorId)) {
            throw new ValidationException(SLOT_CANCEL_CONFLICT);
        }

        requestService.updateStatus(request, InterviewRequestStatus.NEW);
        slotService.updateStatus(slot, ConfirmationStatus.CANCELED);

        return slotMapper.toDto(slot);
    }

    @Transactional
    public void cancelRequest(UUID creatorId, UUID requestId) {
        InterviewRequest request = requestService.getById(requestId);
        requestService.validateCancelableByCreator(creatorId, request);

        Optional<ScheduledInterview> scheduledInterviewOptional =
            scheduledInterviewService.findByRequestId(requestId);

        if (scheduledInterviewOptional.isPresent()) {
            ScheduledInterview scheduledInterview = scheduledInterviewOptional.get();
            scheduledInterviewService.checkCancelable(scheduledInterview);
            scheduledInterviewService.updateStatus(scheduledInterview, ScheduledInterviewStatus.CANCELLED);
        }

        slotService.updateStatus(slotService.getByRequestId(requestId), ConfirmationStatus.CANCELED);
        requestService.updateStatus(request, InterviewRequestStatus.CANCELLED);
    }
}
