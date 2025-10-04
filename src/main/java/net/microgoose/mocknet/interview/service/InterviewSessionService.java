package net.microgoose.mocknet.interview.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.interview.model.InterviewSession;
import net.microgoose.mocknet.interview.repository.InterviewSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewSessionService {

    private final InterviewSessionRepository repository;

    public List<InterviewSession> getAllSessions() {
        return repository.findAll();
    }
}