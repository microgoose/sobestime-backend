package net.sobestime.interview.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.sobestime.app.config.RequestSender;
import net.sobestime.app.dto.PageResponse;
import net.sobestime.auth.model.UserPrincipal;
import net.sobestime.interview.dto.interview_slot.CreateInterviewSlotRequest;
import net.sobestime.interview.dto.interview_slot.InterviewRequestSlotDto;
import net.sobestime.interview.dto.interview_slot.InterviewSlotDto;
import net.sobestime.interview.service.InterviewSlotService;
import net.sobestime.interview.service.InterviewUseCaseService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/interview-slots")
@RequiredArgsConstructor
@Tag(name = "Слоты")
public class InterviewSlotController {

    private final InterviewSlotService interviewSlotService;
    private final InterviewUseCaseService interviewUseCaseService;

    @Operation(summary = "Получить слоты по заявке")
    @GetMapping("/{interviewRequest}")
    public PageResponse<InterviewRequestSlotDto> getSlotsByRequest(@PathVariable UUID interviewRequest,
                                                                   @PageableDefault(size = 20) Pageable pageable) {
        return PageResponse.of(interviewSlotService.viewByRequestId(interviewRequest, pageable));
    }

    @Operation(summary = "Создать слоты", description = "Предложение времени проведения интервью")
    @PostMapping
    public List<InterviewSlotDto> createSlots(@RequestSender UserPrincipal user,
                                              @RequestBody @Valid CreateInterviewSlotRequest request) {
        return interviewUseCaseService.createSlots(user.getId(), request);
    }

    @Operation(summary = "Отменить слоты", description = "Отменить определённое время проведения интервью")
    @PostMapping("{slotId}/cancel")
    public InterviewSlotDto cancelSlots(@RequestSender UserPrincipal user,
                                        @PathVariable UUID slotId) {
        return interviewUseCaseService.cancelSlot(user.getId(), slotId);
    }

    @Operation(summary = "Подтвердить слот", description = "Согласится на предложение по времени для интервью")
    @PostMapping("{slotId}/approve")
    public InterviewSlotDto approveBook(@RequestSender UserPrincipal user,
                                        @PathVariable UUID slotId) {
        return interviewUseCaseService.approveSlot(user.getId(), slotId);
    }

    @Operation(summary = "Отклонить слот", description = "Отклонить предложенное время проведения интервью")
    @PostMapping("{slotId}/reject")
    public InterviewSlotDto rejectBook(@RequestSender UserPrincipal user,
                                       @PathVariable UUID slotId) {
        return interviewUseCaseService.rejectSlot(user.getId(), slotId);
    }
}
