package net.microgoose.mocknet.interview.model;

import lombok.Getter;

@Getter
public enum ScheduledInterviewStatus {
    PLANNED("Планируется"),
    ENDED("Завершено"),
    CANCELLED("Отменено");

    private final String description;

    ScheduledInterviewStatus(String description) {
        this.description = description;
    }
}
