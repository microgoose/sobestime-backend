package net.sobestime.interview.service;

import lombok.RequiredArgsConstructor;
import net.sobestime.app.config.DateTimeService;
import net.sobestime.intermediate.dto.UserRegisterEvent;
import net.sobestime.interview.dto.interview_user.UserInterviewRequestsDto;
import net.sobestime.interview.dto.interview_user.UserInterviewSlotDto;
import net.sobestime.interview.dto.interview_user.UserScheduledInterviewDto;
import net.sobestime.interview.mapper.InterviewRequestMapper;
import net.sobestime.interview.mapper.InterviewUserMapper;
import net.sobestime.interview.mapper.ScheduledInterviewMapper;
import net.sobestime.interview.mapper.StatusMapper;
import net.sobestime.interview.model.InterviewRequest;
import net.sobestime.interview.model.InterviewSlot;
import net.sobestime.interview.model.InterviewUser;
import net.sobestime.interview.repository.InterviewRequestRepository;
import net.sobestime.interview.repository.InterviewSlotRepository;
import net.sobestime.interview.repository.InterviewUserRepository;
import net.sobestime.interview.repository.ScheduledInterviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterviewUserService {

    private final InterviewUserRepository userRepository;
    private final InterviewRequestRepository requestRepository;
    private final ScheduledInterviewRepository interviewRepository;
    private final InterviewSlotRepository slotRepository;
    private final ScheduledInterviewMapper interviewMapper;
    private final InterviewRequestMapper requestMapper;
    private final InterviewUserMapper userMapper;
    private final StatusMapper statusMapper;
    private final DateTimeService dateTimeService;

    public Page<UserInterviewRequestsDto> findUserRequests(UUID creatorId, Pageable pageable) {
        return requestMapper.toUserRequestDto(requestRepository.findByCreator_Id(creatorId, pageable));
    }

    public Page<UserScheduledInterviewDto> findUserInterviews(UUID userId, Pageable pageable) {
        return interviewMapper.toDto(interviewRepository.findUserInterviews(userId, pageable));
    }

    public Page<UserInterviewSlotDto> findUserSlots(UUID userId, Pageable pageable) {
        Page<InterviewSlot> slots = slotRepository.findByBooker_Id(userId, pageable);
        List<UserInterviewSlotDto> requestSlots = new ArrayList<>();

        for (InterviewSlot slot : slots) {
            InterviewRequest request = slot.getRequest();

            requestSlots.add(UserInterviewSlotDto.builder()
                .interviewTitle(request.getTitle())
                .status(statusMapper.toDto(slot.getStatus()))
                .startTime(dateTimeService.toOffsetDateTime(slot.getStartTime()))
                .creator(userMapper.toDto(request.getCreator()))
                .build());
        }

        return new PageImpl<>(
            requestSlots,
            pageable,
            slots.getTotalElements()
        );
    }

    @Transactional
    public InterviewUser saveUser(UserRegisterEvent userEvent) {
        return userRepository.save(InterviewUser.builder()
            .id(userEvent.getId())
            .avatarUrl("https://cdn.sobeshelp.ru/avatars/45.png")
            .username(userEvent.getUsername())
            .build());
    }

}