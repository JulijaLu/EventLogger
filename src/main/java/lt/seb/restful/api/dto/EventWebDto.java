package lt.seb.restful.api.dto;

import lombok.Builder;
import lt.seb.restful.api.dto.enums.MessageType;

@Builder
public record EventWebDto(
        MessageType type,
        String message,
        int userId,
        int transactionId
) {


}
