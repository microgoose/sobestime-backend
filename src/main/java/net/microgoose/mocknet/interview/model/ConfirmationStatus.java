package net.microgoose.mocknet.interview.model;

import lombok.Getter;

@Getter
public enum ConfirmationStatus {
    PENDING("Ожидает"),
    CONFIRMED("Подтвержден"),
    REJECTED("Отклонен"),
    CANCELED("Отменён");

    private final String description;

    ConfirmationStatus(String description) {
        this.description = description;
    }
}
