package net.sobestime.interview.model;

import lombok.Getter;

@Getter
public enum ScheduledInterviewStatus {
    PLANNED("Планируется"),
    RUN("В эфире"),
    ENDED("Завершено"),
    CANCELLED("Отменено");

    private final String description;

    ScheduledInterviewStatus(String description) {
        this.description = description;
    }
}
