package net.microgoose.mocknet.controller;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.dto.CreateInterviewSlotRequest;
import net.microgoose.mocknet.dto.InterviewSlotDto;
import net.microgoose.mocknet.service.InterviewSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/interview-slots")
@RequiredArgsConstructor
public class InterviewSlotController {

    private final InterviewSlotService service;

    @GetMapping
    public List<InterviewSlotDto> getAllSlots() {
        return service.getAllSlots();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InterviewSlotDto createSlot(@RequestBody CreateInterviewSlotRequest slot) {
        return service.createSlot(slot);
    }

    @GetMapping("/available")
    public List<InterviewSlotDto> getAvailableSlots(@RequestParam OffsetDateTime after) {
        return service.getAvailableSlotsAfter(after);
    }

    @GetMapping("/{id}")
    public InterviewSlotDto getSlot(@PathVariable UUID id) {
        return service.getSlotById(id);
    }
}
