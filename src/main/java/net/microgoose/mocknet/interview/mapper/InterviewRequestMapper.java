package net.microgoose.mocknet.interview.mapper;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.config.DateTimeService;
import net.microgoose.mocknet.interview.dto.CreateInterviewRequest;
import net.microgoose.mocknet.interview.dto.InterviewRequestDto;
import net.microgoose.mocknet.interview.model.InterviewRequest;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InterviewRequestMapper {

    private final ProgrammingLanguageMapper programmingLanguageMapper;
    private final DateTimeService dateTimeService;

    public InterviewRequest fromDto(UUID creatorId, CreateInterviewRequest request) {
        return InterviewRequest.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .creatorId(creatorId)
            .programmingLanguage(programmingLanguageMapper.map(request.getProgrammingLanguageId()))
            .createdAt(Instant.now())
            .build();
    }

    public InterviewRequestDto toDto(InterviewRequest request) {
        return InterviewRequestDto.builder()
            .id(request.getId())
            .title(request.getTitle())
            .description(request.getDescription())
            .programmingLanguageId(request.getProgrammingLanguage().getId())
            .creatorId(request.getCreatorId())
            .createdAt(dateTimeService.toOffsetDateTime(request.getCreatedAt()))
            .build();
    }

    public List<InterviewRequestDto> toDto(List<InterviewRequest> request) {
        return request.stream().map(this::toDto).toList();
    }
}
