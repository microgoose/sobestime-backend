package net.microgoose.mocknet.controller;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.dto.CreateSessionRequest;
import net.microgoose.mocknet.dto.InterviewSessionDto;
import net.microgoose.mocknet.dto.SlotBookingDto;
import net.microgoose.mocknet.service.InterviewSessionService;
import net.microgoose.mocknet.service.SlotBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/interview-sessions")
@RequiredArgsConstructor
public class InterviewSessionController {

    private final InterviewSessionService sessionService;
    private final SlotBookingService bookingService;

    @GetMapping
    public List<InterviewSessionDto> getAllSessions() {
        return sessionService.getAllSessions();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public InterviewSessionDto createSession(@RequestBody CreateSessionRequest request) {
        return sessionService.createSession(request);
    }

    @GetMapping("/by-slot/{slotId}")
    public InterviewSessionDto getSessionBySlot(@PathVariable UUID slotId) {
        SlotBookingDto booking = bookingService.getBookingBySlot(slotId);
        return sessionService.getSessionBySlotBooking(booking.getId());
    }
}
