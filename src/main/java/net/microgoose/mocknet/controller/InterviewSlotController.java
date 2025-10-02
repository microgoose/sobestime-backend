package net.microgoose.mocknet.controller;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.dto.CreateInterviewSlotRequest;
import net.microgoose.mocknet.dto.InterviewSlotDto;
import net.microgoose.mocknet.mapper.InterviewSlotMapper;
import net.microgoose.mocknet.service.InterviewSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/interview-slots")
@RequiredArgsConstructor
public class InterviewSlotController {

    private final InterviewSlotService service;
    private final InterviewSlotMapper mapper;

    @GetMapping
    public List<InterviewSlotDto> getAllSlots() {
        return mapper.toDto(service.getAllSlots());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InterviewSlotDto createSlot(@RequestBody CreateInterviewSlotRequest slot) {
        return mapper.toDto(service.createSlot(slot));
    }

    @GetMapping("/{id}")
    public InterviewSlotDto getSlot(@PathVariable UUID id) {
        return mapper.toDto(service.getSlotById(id));
    }
}
