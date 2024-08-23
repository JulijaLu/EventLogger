package lt.seb.restful.mapping;

import lt.seb.restful.api.dto.EventWebDto;
import lt.seb.restful.model.Event;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventMappingService {

    public EventWebDto toEventWebDto(Event event) {
        return EventWebDto.builder()
                .message(event.getMessage())
                .type(event.getType())
                .userId(event.getUserId())
                .transactionId(event.getTransactionId())
                .build();
    }

    public Event toEvent(EventWebDto eventWebDto) {
        return Event.builder()
                .message(eventWebDto.message())
                .type(eventWebDto.type())
                .userId(eventWebDto.userId())
                .transactionId(eventWebDto.transactionId())
                .build();
    }

    public List<EventWebDto> eventWebDtoList(List<Event> eventList) {
        return eventList.stream()
                .map(this::toEventWebDto)
                .toList();
    }
}
