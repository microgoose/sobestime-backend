package net.microgoose.mocknet.interview.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.config.RequestSender;
import net.microgoose.mocknet.auth.model.UserPrincipal;
import net.microgoose.mocknet.interview.dto.BookInterviewSlotRequest;
import net.microgoose.mocknet.interview.dto.InterviewSlotDto;
import net.microgoose.mocknet.interview.service.InterviewSlotService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/interview-slots")
@RequiredArgsConstructor
public class InterviewSlotController {

    private final InterviewSlotService interviewSlotService;

    @PostMapping
    public InterviewSlotDto createInterviewRequests(@RequestSender UserPrincipal user,
                                                    @RequestBody @Valid BookInterviewSlotRequest request) {

        return interviewSlotService.bookSlot(user.getId(), request);
    }
}
