package net.microgoose.mocknet.interview.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.intermediate.dto.UserRegisterEvent;
import net.microgoose.mocknet.interview.model.InterviewUser;
import net.microgoose.mocknet.interview.repository.InterviewUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterviewUserService {

    private final InterviewUserRepository repository;

    @Transactional
    public InterviewUser saveUser(UserRegisterEvent userEvent) {
        return repository.save(InterviewUser.builder()
            .id(userEvent.getId())
            .avatarUrl("https://cdn.sobeshelp.ru/avatars/45.png")
            .username(userEvent.getUsername())
            .build());
    }

}