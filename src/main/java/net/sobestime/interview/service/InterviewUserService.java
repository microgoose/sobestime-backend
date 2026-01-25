package net.sobestime.interview.service;

import lombok.RequiredArgsConstructor;
import net.sobestime.app.exception.ValidationException;
import net.sobestime.intermediate.dto.UserRegisterEvent;
import net.sobestime.interview.dto.interview_user.UserInterviewRequestsDto;
import net.sobestime.interview.dto.interview_user.UserInterviewSlotDto;
import net.sobestime.interview.dto.interview_user.UserScheduledInterviewDto;
import net.sobestime.interview.mapper.InterviewRequestMapper;
import net.sobestime.interview.mapper.InterviewSlotMapper;
import net.sobestime.interview.mapper.ScheduledInterviewMapper;
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

import java.util.List;
import java.util.UUID;

import static net.sobestime.interview.config.MessageDictionary.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class InterviewUserService {

    private final InterviewUserRepository userRepository;
    private final InterviewRequestRepository requestRepository;
    private final ScheduledInterviewRepository interviewRepository;
    private final InterviewSlotRepository slotRepository;
    private final InterviewSlotMapper slotMapper;
    private final ScheduledInterviewMapper interviewMapper;
    private final InterviewRequestMapper requestMapper;

    public Page<UserInterviewRequestsDto> getUserRequestsByCreatorId(UUID creatorId, Pageable pageable) {
        return requestMapper.toUserRequestDto(requestRepository.findByCreator_Id(creatorId, pageable));
    }

    public Page<UserScheduledInterviewDto> getUserInterviews(UUID userId, Pageable pageable) {
        return interviewMapper.toDto(interviewRepository.findUserInterviews(userId, pageable));
    }

    public Page<UserInterviewSlotDto> getReceived(UUID userId, Pageable pageable) {
        Page<InterviewSlot> slots = slotRepository.findByRequest_Creator_Id(userId, pageable);
        List<UserInterviewSlotDto> suggestedSlots = slotMapper.toUserSlotDto(slots.getContent());
        return new PageImpl<>(suggestedSlots, pageable, slots.getTotalElements());
    }

    public Page<UserInterviewSlotDto> getSentSlots(UUID userId, Pageable pageable) {
        Page<InterviewSlot> slots = slotRepository.findByBooker_Id(userId, pageable);
        List<UserInterviewSlotDto> suggestedSlots = slotMapper.toUserSlotDto(slots.getContent());
        return new PageImpl<>(suggestedSlots, pageable, slots.getTotalElements());
    }

    public InterviewUser getById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ValidationException(USER_NOT_FOUND));
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