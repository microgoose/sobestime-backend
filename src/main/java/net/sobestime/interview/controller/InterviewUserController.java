package net.sobestime.interview.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.sobestime.app.config.RequestSender;
import net.sobestime.app.dto.PageResponse;
import net.sobestime.auth.model.UserPrincipal;
import net.sobestime.interview.dto.interview_user.UserInterviewRequestsDto;
import net.sobestime.interview.dto.interview_user.UserInterviewSlotDto;
import net.sobestime.interview.dto.interview_user.UserScheduledInterviewDto;
import net.sobestime.interview.service.InterviewUserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/interview-user")
@RequiredArgsConstructor
@Tag(name = "Пользователь интервью")
public class InterviewUserController {

    private final InterviewUserService interviewUserService;

    @Operation(
        summary = "Заявки пользователя",
        description = "Получить список заявок на интервью, созданных текущим пользователем, постранично"
    )
    @GetMapping("/requests")
    public PageResponse<UserInterviewRequestsDto> getUserInterviewRequests(
        @RequestSender UserPrincipal user,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        return PageResponse.of(
            interviewUserService.getUserRequestsByCreatorId(user.getId(), pageable)
        );
    }

    @Operation(
        summary = "Планируемые интервью пользователя",
        description = "Получить список интервью, запланированных для текущего пользователя, постранично"
    )
    @GetMapping("/interviews")
    public PageResponse<UserScheduledInterviewDto> getUserScheduledInterviews(
        @RequestSender UserPrincipal user,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        return PageResponse.of(
            interviewUserService.getUserInterviews(user.getId(), pageable)
        );
    }

    @Operation(
        summary = "Полученные слоты пользователя",
        description = "Получить слоты интервью, предложенные другими пользователями текущему пользователю, постранично"
    )
    @GetMapping("/slots/received")
    public PageResponse<UserInterviewSlotDto> getReceivedInterviewSlots(
        @RequestSender UserPrincipal user,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        return PageResponse.of(
            interviewUserService.getReceived(user.getId(), pageable)
        );
    }

    @Operation(
        summary = "Отправленные слоты пользователя",
        description = "Получить слоты интервью, отправленные текущим пользователем другим пользователям, постранично"
    )
    @GetMapping("/slots/sent")
    public PageResponse<UserInterviewSlotDto> getSentInterviewSlots(
        @RequestSender UserPrincipal user,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        return PageResponse.of(
            interviewUserService.getSentSlots(user.getId(), pageable)
        );
    }
}
