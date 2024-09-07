package lt.seb.restful.api.dto.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageType {
    DEBUG,
    INFO,
    WARNING,
    ERROR;


    public static MessageType fromString(String value) {
        for (MessageType type : MessageType.values()) {
            if (type.toString().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid MessageType value: " + value);
    }
}
