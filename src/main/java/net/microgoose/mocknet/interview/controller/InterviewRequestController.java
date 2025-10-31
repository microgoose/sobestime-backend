package net.microgoose.mocknet.interview.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.config.RequestSender;
import net.microgoose.mocknet.app.dto.PageResponse;
import net.microgoose.mocknet.auth.model.UserPrincipal;
import net.microgoose.mocknet.interview.dto.interview_request.CreateInterviewRequest;
import net.microgoose.mocknet.interview.dto.interview_request.InterviewRequestDto;
import net.microgoose.mocknet.interview.service.InterviewRequestService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/interview-requests")
@RequiredArgsConstructor
@Tag(name = "Заявки на интервью")
public class InterviewRequestController {

    private final InterviewRequestService interviewRequestService;

    @Operation(summary = "Получить заявки", description = "Получить все заявки постранично")
    @GetMapping
    public PageResponse<InterviewRequestDto> getInterviewRequests(@PageableDefault(size = 20) Pageable pageable) {
        return PageResponse.of(interviewRequestService.findAll(pageable));
    }

    @Operation(summary = "Создать заявку")
    @PostMapping
    public InterviewRequestDto createInterviewRequests(@RequestSender UserPrincipal user,
                                                       @RequestBody @Valid CreateInterviewRequest request) {

        return interviewRequestService.saveRequest(user.getId(), request);
    }
}
