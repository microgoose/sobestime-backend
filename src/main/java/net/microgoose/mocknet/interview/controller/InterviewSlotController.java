package net.microgoose.mocknet.interview.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.config.RequestSender;
import net.microgoose.mocknet.app.dto.PageResponse;
import net.microgoose.mocknet.auth.model.UserPrincipal;
import net.microgoose.mocknet.interview.dto.interview_slot.CreateInterviewSlotRequest;
import net.microgoose.mocknet.interview.dto.interview_slot.InterviewRequestSlotDto;
import net.microgoose.mocknet.interview.dto.interview_slot.InterviewSlotDto;
import net.microgoose.mocknet.interview.service.InterviewSlotService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/interview-slots")
@RequiredArgsConstructor
public class InterviewSlotController {

    private final InterviewSlotService interviewSlotService;

    @GetMapping("/{interviewRequest}")
    public PageResponse<InterviewRequestSlotDto> getSlotsByRequest(@PathVariable UUID interviewRequest,
                                                                   @PageableDefault(size = 20) Pageable pageable) {
        return PageResponse.of(interviewSlotService.findSlotsByRequest(interviewRequest, pageable));
    }

    @PostMapping
    public List<InterviewSlotDto> createSlots(@RequestSender UserPrincipal user,
                                              @RequestBody @Valid CreateInterviewSlotRequest request) {
        return interviewSlotService.createSlots(user.getId(), request);
    }

    @PostMapping("{slotId}/approve")
    public InterviewSlotDto approveBook(@RequestSender UserPrincipal user,
                                        @PathVariable UUID slotId) {
        return interviewSlotService.approveSlot(user.getId(), slotId);
    }

    @PostMapping("{slotId}/reject")
    public InterviewSlotDto rejectBook(@RequestSender UserPrincipal user,
                                       @PathVariable UUID slotId) {
        return interviewSlotService.rejectSlot(user.getId(), slotId);
    }
}
