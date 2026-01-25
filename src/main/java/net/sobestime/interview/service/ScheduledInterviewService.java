package net.sobestime.interview.service;

import lombok.RequiredArgsConstructor;
import net.sobestime.app.exception.ValidationException;
import net.sobestime.interview.model.InterviewRequest;
import net.sobestime.interview.model.InterviewSlot;
import net.sobestime.interview.model.ScheduledInterview;
import net.sobestime.interview.model.ScheduledInterviewStatus;
import net.sobestime.interview.repository.ScheduledInterviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static net.sobestime.interview.config.MessageDictionary.*;

@Service
@RequiredArgsConstructor
public class ScheduledInterviewService {

    private final ScheduledInterviewRepository interviewRepository;

    public Optional<ScheduledInterview> findByRequestId(UUID requestId) {
        return interviewRepository.findByRequest_Id(requestId);
    }

    public ScheduledInterview getByRequestId(UUID requestId) {
        return findByRequestId(requestId)
            .orElseThrow(() -> new ValidationException(SCHEDULED_INTERVIEW_NOT_FOUND));
    }

    @Transactional
    public ScheduledInterview updateStatus(ScheduledInterview request, ScheduledInterviewStatus status) {
        request.setStatus(status);
        return interviewRepository.save(request);
    }

    @Transactional
    public ScheduledInterview createScheduledInterview(InterviewRequest request, InterviewSlot slot) {
        // TODO several slots at same time?
        return interviewRepository.save(ScheduledInterview.builder()
            .status(ScheduledInterviewStatus.PLANNED)
            .meetingLink("https://www.youtube.com/watch?v=dQw4w9WgXcQ&list=RDdQw4w9WgXcQ&start_radio=1")
            .slot(slot)
            .request(request)
            .build());
    }

    public boolean isCancelable(ScheduledInterview scheduledInterview) {
        return scheduledInterview.getStatus() == ScheduledInterviewStatus.CANCELLED ||
            scheduledInterview.getStatus() == ScheduledInterviewStatus.PLANNED;
    }

    public void checkCancelable(ScheduledInterview scheduledInterview) {
        if (scheduledInterview.getStatus() == ScheduledInterviewStatus.RUN) {
            throw new ValidationException(SCHEDULED_INTERVIEW_RUN_CANCEL_CONFLICT);
        }

        if (scheduledInterview.getStatus() == ScheduledInterviewStatus.ENDED) {
            throw new ValidationException(SCHEDULED_INTERVIEW_END_CANCEL_CONFLICT);
        }
    }
}
