package net.microgoose.mocknet.interview.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import net.microgoose.mocknet.interview.config.MessageDictionary;

import java.util.UUID;

@Data
@Builder
public class BookInterviewSlotRequest {
    @NotNull(message = MessageDictionary.SLOT_ID_NOT_SPECIFIED)
    private UUID slotUuid;
}