package net.sobestime.interview.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.sobestime.app.config.RequestSender;
import net.sobestime.app.dto.PageResponse;
import net.sobestime.auth.model.UserPrincipal;
import net.sobestime.interview.dto.interview_request.CreateInterviewRequest;
import net.sobestime.interview.dto.interview_request.InterviewRequestDto;
import net.sobestime.interview.service.InterviewRequestService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @Operation(summary = "Получить заявку", description = "Получить конкретную заявку")
    @GetMapping("/{requestId}")
    public InterviewRequestDto getInterviewRequests(@PathVariable UUID requestId) {
        return interviewRequestService.findById(requestId);
    }

    @Operation(summary = "Создать заявку")
    @PostMapping
    public InterviewRequestDto createInterviewRequests(@RequestSender UserPrincipal user,
                                                       @RequestBody @Valid CreateInterviewRequest request) {

        return interviewRequestService.saveRequest(user.getId(), request);
    }
}
