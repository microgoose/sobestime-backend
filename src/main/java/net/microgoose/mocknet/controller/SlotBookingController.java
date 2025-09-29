package net.microgoose.mocknet.controller;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.dto.CreateSlotBookingRequest;
import net.microgoose.mocknet.dto.SlotBookingDto;
import net.microgoose.mocknet.service.SlotBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/slot-bookings")
@RequiredArgsConstructor
public class SlotBookingController {

    private final SlotBookingService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SlotBookingDto bookSlot(@RequestBody CreateSlotBookingRequest request) {
        return service.createSlotBooking(request);
    }

    @GetMapping("/by-interviewer/{interviewerId}")
    public List<SlotBookingDto> getBookingsByInterviewer(@PathVariable UUID interviewerId) {
        return service.getBookingsByInterviewer(interviewerId);
    }

    @GetMapping("/by-slot/{slotId}")
    public SlotBookingDto getBookingBySlot(@PathVariable UUID slotId) {
        return service.getBookingBySlot(slotId);
    }
}
