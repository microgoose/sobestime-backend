package net.microgoose.mocknet.controller;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.dto.CreateInterviewRequest;
import net.microgoose.mocknet.dto.InterviewRequestDto;
import net.microgoose.mocknet.mapper.InterviewRequestMapper;
import net.microgoose.mocknet.service.InterviewRequestService;
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
    public InterviewRequestDto createRequest(@RequestBody CreateInterviewRequest request) {
        return mapper.toDto(service.createRequest(request));
    }

    @GetMapping("/{id}")
    public InterviewRequestDto getRequest(@PathVariable UUID id) {
        return mapper.toDto(service.getRequestById(id));
    }
}
