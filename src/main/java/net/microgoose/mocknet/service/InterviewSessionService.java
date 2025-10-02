package net.microgoose.mocknet.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.model.InterviewSession;
import net.microgoose.mocknet.repository.InterviewSessionRepository;
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