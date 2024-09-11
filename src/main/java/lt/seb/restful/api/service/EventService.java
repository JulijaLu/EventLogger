package lt.seb.restful.api.service;

import lt.seb.restful.api.dto.EventDto;

import java.util.List;

public interface EventService {
    EventDto findById(int id);
    List<EventDto> findAll();
    EventDto createEvent(EventDto event);
    EventDto updateEvent(EventDto event, int id);
    void delete(int id);
    List<EventDto> filterEvents(String fieldName);
}
