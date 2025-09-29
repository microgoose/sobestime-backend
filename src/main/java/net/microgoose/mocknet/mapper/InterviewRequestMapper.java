package net.microgoose.mocknet.mapper;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.config.DateTimeService;
import net.microgoose.mocknet.dto.CreateInterviewRequest;
import net.microgoose.mocknet.dto.InterviewRequestDto;
import net.microgoose.mocknet.model.InterviewRequest;
import net.microgoose.mocknet.model.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InterviewRequestMapper {

    private final ProgrammingLanguageMapper programmingLanguageMapper;
    private final DateTimeService dateTimeService;

    public InterviewRequest fromDto(CreateInterviewRequest request) {
        return InterviewRequest.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .creator(User.builder().id(request.getCreatorId()).build())
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
            .creatorId(request.getCreator().getId())
            .createdAt(dateTimeService.toOffsetDateTime(request.getCreatedAt()))
            .build();
    }

    public List<InterviewRequestDto> toDto(List<InterviewRequest> request) {
        return request.stream().map(this::toDto).toList();
    }
}
