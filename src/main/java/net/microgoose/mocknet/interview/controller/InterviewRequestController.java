package net.microgoose.mocknet.interview.controller;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.interview.dto.CreateInterviewRequest;
import net.microgoose.mocknet.interview.dto.InterviewRequestDto;
import net.microgoose.mocknet.interview.mapper.InterviewRequestMapper;
import net.microgoose.mocknet.interview.service.InterviewRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/interview-requests")
@RequiredArgsConstructor
public class InterviewRequestController {

    private final InterviewRequestService service;
    private final InterviewRequestMapper mapper;

    @GetMapping
    public List<InterviewRequestDto> getAllRequests() {
        return mapper.toDto(service.getAllRequests());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InterviewRequestDto createRequest(@RequestAttribute("X-User-Id") UUID userId,
                                             @RequestBody CreateInterviewRequest request) {

        return mapper.toDto(service.createRequest(userId, request));
    }

    @GetMapping("/{id}")
    public InterviewRequestDto getRequest(@PathVariable UUID id) {
        return mapper.toDto(service.getRequestById(id));
    }
}
