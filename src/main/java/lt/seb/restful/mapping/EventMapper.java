package lt.seb.restful.mapping;

import lt.seb.restful.api.dto.EventDto;
import lt.seb.restful.api.dto.enums.MessageType;
import lt.seb.restful.model.Event;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventDto convertEventToEventDto(Event event);

    Event convertEventDtoToEvent(EventDto eventDto);

    List<EventDto> convertEventListToEventDtoList(List<Event> eventList);

    default String messageTypeToString(MessageType messageType) {
        if (messageType == null) {
            return null;
        }
        return messageType.toString();
    }

    default MessageType stringToMessageType(String messageType) {
        if (messageType == null) {
            return null;
        }
        return MessageType.valueOf(messageType);
    }
}
