package net.microgoose.mocknet.controller;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.dto.CreateSlotBookingRequest;
import net.microgoose.mocknet.dto.SlotBookingDto;
import net.microgoose.mocknet.mapper.SlotBookingMapper;
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
    private final SlotBookingMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SlotBookingDto bookSlot(@RequestBody CreateSlotBookingRequest request) {
        return mapper.toDto(service.createSlotBooking(request));
    }

    @GetMapping("/by-interviewer/{interviewerId}")
    public List<SlotBookingDto> getBookingsByInterviewer(@PathVariable UUID interviewerId) {
        return mapper.toDto(service.getBookingsByInterviewer(interviewerId));
    }

    @GetMapping("/by-slot/{slotId}")
    public SlotBookingDto getBookingBySlot(@PathVariable UUID slotId) {
        return mapper.toDto(service.getBookingBySlot(slotId));
    }
}
