package lt.seb.restful.mapping;

import lt.seb.restful.api.dto.EventWebDto;
import lt.seb.restful.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface MapstructMapping {
    EventWebDto eventToEventDto(Optional<Event> event);
    Event eventWebDtoToEvent(EventWebDto eventWebDto);
    List<EventWebDto> eventToEventWebDto(List<Event> eventList);
}
