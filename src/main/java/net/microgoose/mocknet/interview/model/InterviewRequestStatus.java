package net.microgoose.mocknet.interview.model;

import lombok.Getter;

@Getter
public enum InterviewRequestStatus {
    NEW("Новая"),
    PLANNED("Планируемая"),
    ENDED("Закончена"),
    CANCELLED("Отменена");

    private final String description;

    InterviewRequestStatus(String description) {
        this.description = description;
    }
}
