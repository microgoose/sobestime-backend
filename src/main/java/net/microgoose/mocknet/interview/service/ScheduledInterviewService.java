package net.microgoose.mocknet.interview.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.interview.model.InterviewRequest;
import net.microgoose.mocknet.interview.model.InterviewSlot;
import net.microgoose.mocknet.interview.model.ScheduledInterview;
import net.microgoose.mocknet.interview.model.ScheduledInterviewStatus;
import net.microgoose.mocknet.interview.repository.ScheduledInterviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduledInterviewService {

    private final ScheduledInterviewRepository interviewRepository;

    @Transactional
    public void createScheduledInterview(InterviewRequest request, InterviewSlot slot) {
        ScheduledInterview scheduledInterview = ScheduledInterview.builder()
            .status(ScheduledInterviewStatus.PLANNED)
            .meetingLink("https://www.youtube.com/watch?v=dQw4w9WgXcQ&list=RDdQw4w9WgXcQ&start_radio=1")
            .slot(slot)
            .request(request)
            .build();

        interviewRepository.save(scheduledInterview);
    }
}
