package net.microgoose.mocknet.interview.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.intermediate.dto.UserRegisterEvent;
import net.microgoose.mocknet.interview.dto.interview_user.UserInterviewDto;
import net.microgoose.mocknet.interview.dto.interview_user.UserInterviewRequestsDto;
import net.microgoose.mocknet.interview.dto.interview_user.UserInterviewReservationsDto;
import net.microgoose.mocknet.interview.mapper.InterviewRequestMapper;
import net.microgoose.mocknet.interview.model.InterviewUser;
import net.microgoose.mocknet.interview.repository.InterviewRequestRepository;
import net.microgoose.mocknet.interview.repository.InterviewUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterviewUserService {

    private final InterviewUserRepository userRepository;
    private final InterviewRequestRepository requestRepository;

    private final InterviewRequestMapper requestMapper;

    public Page<UserInterviewRequestsDto> findUserRequests(UUID creatorId, Pageable pageable) {
        return requestMapper.toUserRequestDto(requestRepository.findByCreator_Id(creatorId, pageable));
    }

    public Page<UserInterviewDto> findUserInterviews(UUID userId, Pageable pageable) {
        return null; // TODO
    }

    public Page<UserInterviewReservationsDto> findUserSlots(UUID userId, Pageable pageable) {
        return null; // TODO
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