package lt.seb.restful.api.service;

import lombok.AllArgsConstructor;
import lt.seb.restful.api.dto.EventWebDto;
import lt.seb.restful.exception.EventNotFoundException;
import lt.seb.restful.mapping.EventMapper;
import lt.seb.restful.model.Event;
import lt.seb.restful.repository.EventRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final Logger log = LogManager.getLogger("events");

    @Override
    public List<EventWebDto> findAll() {
        List<Event> eventList = eventRepository.findAll();
        return eventMapper.eventsToEventWebDtos(eventList);
    }

    @Override
    public EventWebDto findById(int id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("event not found with id: {}", id);
                    return new EventNotFoundException();
                });
        log.info("Event found: {}", event);
        return eventMapper.eventToEventDto(event);
    }

    @Override
    public EventWebDto findByType(String type) {
        Event event = eventRepository.findByType(type)
                .orElseThrow(() -> {
                    return new EventNotFoundException();
                });
        return eventMapper.eventToEventDto(event);
    }

    @Override
    public EventWebDto createEvent(EventWebDto eventWebDto) {
        Event event = eventMapper.eventWebDtoToEvent(eventWebDto);
        int id = eventRepository.createEvent(event);
        Event result = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not created"));
        log.info("Event successfully created");
        return eventMapper.eventToEventDto(result);
    }

    @Override
    public EventWebDto updateEvent(EventWebDto eventWebDto, int id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("event not found with id: {}", id);
                    return new EventNotFoundException("event not found with id: " + id);
                });
        event.setMessage(eventWebDto.message())
                .setType(eventWebDto.type().toString())
                .setUserId(eventWebDto.userId())
                .setTransactionId(eventWebDto.transactionId());
        eventRepository.updateEvent(event);
        event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("event not found with id: {}", id);
                    return new EventNotFoundException("event not found with id: " + id);
                });
        log.info("Event {} updated", eventWebDto);
        return eventMapper.eventToEventDto(event);
    }

    @Override
    public void delete(int id) {
        eventRepository.deleteEvent(id);
    }
}
