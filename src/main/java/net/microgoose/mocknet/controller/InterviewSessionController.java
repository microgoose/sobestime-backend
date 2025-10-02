package net.microgoose.mocknet.controller;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.dto.InterviewSessionDto;
import net.microgoose.mocknet.mapper.InterviewSessionMapper;
import net.microgoose.mocknet.service.InterviewSessionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/interview-sessions")
@RequiredArgsConstructor
public class InterviewSessionController {

    private final InterviewSessionService sessionService;
    private final InterviewSessionMapper sessionMapper;

    @GetMapping
    public List<InterviewSessionDto> getAllSessions() {
        return sessionMapper.toDto(sessionService.getAllSessions());
    }
}
