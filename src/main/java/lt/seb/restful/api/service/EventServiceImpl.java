package lt.seb.restful.api.service;

import lombok.AllArgsConstructor;
import lt.seb.restful.api.dto.EventDto;
import lt.seb.restful.exception.EventNotFoundException;
import lt.seb.restful.mapping.EventMapper;
import lt.seb.restful.model.Event;
import lt.seb.restful.repository.EventRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final Logger log = LogManager.getLogger("events");

    @Override
    public List<EventDto> findAll() {
        List<Event> eventList = eventRepository.findAll();
        return eventMapper.convertEventListToEventDtoList(eventList);
    }

    @Override
    public EventDto findById(int id) {
        Event event = findEventOrThrowException(id);
        log.info("Event found: {}", event);
        return eventMapper.convertEventToEventDto(event);
    }

    @Override
    public EventDto createEvent(EventDto eventDto) {
        Event event = eventMapper.convertEventDtoToEvent(eventDto);
        int id = eventRepository.createEvent(event);
        Event result = findEventOrThrowException(id);
        log.info("Event successfully created");
        return eventMapper.convertEventToEventDto(result);
    }

    @Override
    public EventDto updateEvent(EventDto eventDto, int id) {
        Event event = findEventOrThrowException(id);
        updateEventFields(event, eventDto);
        eventRepository.updateEvent(event);
        log.info("Event {} updated", eventDto);
        return eventMapper.convertEventToEventDto(event);
    }

    @Override
    public void delete(int id) {
        eventRepository.deleteEvent(id);
    }

    @Override
    public List<EventDto> filterEvents(String fieldName) {
        List<Event> events = eventRepository.findAll();
        List<Event> filteredEvents = filterEvents(events, fieldName);
        return eventMapper.convertEventListToEventDtoList(filteredEvents);
    }

    private List<Event> filterEvents(List<Event> events, String fieldName) {
        return events.stream()
                .filter(event -> {
                    if (event.getType() != null && event.getType().equals(fieldName)) {
                        return true;
                    } else if (event.getMessage() != null && event.getMessage().contains(fieldName)) {
                        return true;
                    } else if (Integer.parseInt(fieldName) == event.getUserId()) {
                        return true;
                    } else if (Integer.parseInt(fieldName) == event.getTransactionId()) {
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    public void updateEventFields(Event event, EventDto eventDto) {
        event.setMessage(eventDto.message())
                .setType(eventDto.type().toString())
                .setUserId(eventDto.userId())
                .setTransactionId(eventDto.transactionId());
    }

    public Event findEventOrThrowException(int id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("event not found with id: {}", id);
                    return new EventNotFoundException("event not found with id: " + id);
                });
    }
}
