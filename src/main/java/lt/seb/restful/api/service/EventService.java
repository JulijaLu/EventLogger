package lt.seb.restful.api.service;

import lt.seb.restful.api.dto.EventWebDto;
import lt.seb.restful.model.Event;

import java.util.List;
import java.util.Optional;


public interface EventService {
    EventWebDto findById(int id);
    List<EventWebDto> findAll();
    EventWebDto createEvent(EventWebDto event);
    EventWebDto updateEvent(EventWebDto event, int id);
    void delete(int id);
    EventWebDto getEventWebDtoFromEvent(Optional<Event> event);
}
