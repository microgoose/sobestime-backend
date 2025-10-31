package net.microgoose.mocknet.interview.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.config.DateTimeService;
import net.microgoose.mocknet.intermediate.dto.UserRegisterEvent;
import net.microgoose.mocknet.interview.dto.interview_user.UserInterviewRequestsDto;
import net.microgoose.mocknet.interview.dto.interview_user.UserInterviewSlotDto;
import net.microgoose.mocknet.interview.dto.interview_user.UserScheduledInterviewDto;
import net.microgoose.mocknet.interview.mapper.InterviewRequestMapper;
import net.microgoose.mocknet.interview.mapper.InterviewUserMapper;
import net.microgoose.mocknet.interview.mapper.ScheduledInterviewMapper;
import net.microgoose.mocknet.interview.mapper.StatusMapper;
import net.microgoose.mocknet.interview.model.InterviewRequest;
import net.microgoose.mocknet.interview.model.InterviewSlot;
import net.microgoose.mocknet.interview.model.InterviewUser;
import net.microgoose.mocknet.interview.repository.InterviewRequestRepository;
import net.microgoose.mocknet.interview.repository.InterviewSlotRepository;
import net.microgoose.mocknet.interview.repository.InterviewUserRepository;
import net.microgoose.mocknet.interview.repository.ScheduledInterviewRepository;
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