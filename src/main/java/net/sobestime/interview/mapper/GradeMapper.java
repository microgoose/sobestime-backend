package net.sobestime.interview.mapper;

import lombok.RequiredArgsConstructor;
import net.sobestime.interview.dto.grade.GradeDto;
import net.sobestime.interview.model.Grade;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GradeMapper {

    public GradeDto toDto(Grade grade) {
        return GradeDto.builder()
            .uuid(grade.getId())
            .name(grade.getName())
            .build();
    }

    public Set<GradeDto> toDto(Set<Grade> items) {
        return items.stream().map(this::toDto).collect(Collectors.toSet());
    }
}
