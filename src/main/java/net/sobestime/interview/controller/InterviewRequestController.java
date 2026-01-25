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
import net.sobestime.interview.dto.interview_request.UpdateInterviewRequest;
import net.sobestime.interview.service.InterviewRequestService;
import net.sobestime.interview.service.InterviewUseCaseService;
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
    private final InterviewUseCaseService interviewUseCaseService;

    @Operation(summary = "Получить все интервью", description = "Получить все интервью постранично")
    @GetMapping
    public PageResponse<InterviewRequestDto> getInterviewRequests(@PageableDefault(size = 20) Pageable pageable) {
        return PageResponse.of(interviewRequestService.getAll(pageable));
    }

    @Operation(summary = "Получить интервью", description = "Получить конкретное интервью")
    @GetMapping("/{requestId}")
    public InterviewRequestDto getInterviewRequests(@PathVariable UUID requestId) {
        return interviewRequestService.viewById(requestId);
    }

    @Operation(summary = "Создать запрос на проведение интервью")
    @PostMapping
    public InterviewRequestDto createInterviewRequest(@RequestSender UserPrincipal user,
                                                      @RequestBody @Valid CreateInterviewRequest request) {

        return interviewRequestService.createRequest(user.getId(), request);
    }

    @Operation(summary = "Обновить запрос на интервью")
    @PatchMapping("/{requestId}")
    public InterviewRequestDto updateInterviewRequest(@RequestSender UserPrincipal user,
                                                      @PathVariable UUID requestId,
                                                      @RequestBody @Valid UpdateInterviewRequest request) {

        return interviewRequestService.updateRequest(user.getId(), requestId, request);
    }

    @Operation(summary = "Отменить запрос на интервью")
    @PatchMapping("/{requestId}/cancel")
    public void cancelInterviewRequest(@RequestSender UserPrincipal user,
                                       @PathVariable UUID requestId) {
        interviewUseCaseService.cancelRequest(user.getId(), requestId);
    }
}
