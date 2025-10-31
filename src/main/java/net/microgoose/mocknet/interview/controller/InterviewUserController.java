package net.microgoose.mocknet.interview.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.config.RequestSender;
import net.microgoose.mocknet.app.dto.PageResponse;
import net.microgoose.mocknet.auth.model.UserPrincipal;
import net.microgoose.mocknet.interview.dto.interview_user.UserInterviewRequestsDto;
import net.microgoose.mocknet.interview.dto.interview_user.UserInterviewSlotDto;
import net.microgoose.mocknet.interview.dto.interview_user.UserScheduledInterviewDto;
import net.microgoose.mocknet.interview.service.InterviewUserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/interview-user")
@RequiredArgsConstructor
@Tag(name = "Пользователь")
public class InterviewUserController {

    private final InterviewUserService interviewUserService;

    @Operation(summary = "Заявки пользователя", description = "Получить заявки пользователя постранично")
    @GetMapping("/requests")
    public PageResponse<UserInterviewRequestsDto> getUserRequests(@RequestSender UserPrincipal user,
                                                          @PageableDefault(size = 20) Pageable pageable) {
        return PageResponse.of(interviewUserService.findUserRequests(user.getId(), pageable));
    }

    @Operation(summary = "Планируемые интервью пользователя", description = "Получить планируемые интервью пользователя постранично")
    @GetMapping("/interviews")
    public PageResponse<UserScheduledInterviewDto> getUserInterviews(@RequestSender UserPrincipal user,
                                                                     @PageableDefault(size = 20) Pageable pageable) {
        return PageResponse.of(interviewUserService.findUserInterviews(user.getId(), pageable));
    }

    @Operation(summary = "Слоты пользователя", description = "Получить поданные слоты пользователя постранично")
    @GetMapping("/slots")
    public PageResponse<UserInterviewSlotDto> getUserSlots(@RequestSender UserPrincipal user,
                                                           @PageableDefault(size = 20) Pageable pageable) {
        return PageResponse.of(interviewUserService.findUserSlots(user.getId(), pageable));
    }
}
