package lt.seb.restful.api.service;

import lombok.AllArgsConstructor;
import lt.seb.restful.api.dto.EventWebDto;
import lt.seb.restful.exception.EventException;
import lt.seb.restful.mapping.EventMappingService;
import lt.seb.restful.mapping.MapstructMapping;
import lt.seb.restful.model.Event;
import lt.seb.restful.repository.EventRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private MapstructMapping mapping;

    private final EventRepository eventRepository;
    private final EventMappingService eventMappingService;
    private final Logger log = LogManager.getLogger("events");

    @Override
    public List<EventWebDto> findAll() {
        List<Event> eventList = eventRepository.findAll();
        return mapping.eventToEventWebDto(eventList);
//        return eventMappingService.eventWebDtoList(eventRepository.findAll());
    }

    @Override
    public EventWebDto findById(int id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("event not found with id: {}", id);
                    return new EventException("Event not found");
                });
        log.info("Event found: {}", event);
        return eventMappingService.toEventWebDto(event);
    }

    @Override
    public EventWebDto createEvent(EventWebDto eventWebDto) {
        Event event = eventMappingService.toEvent(eventWebDto);
        int id = eventRepository.createEvent(event);
        Event result = eventRepository.findById(id)
                .orElseThrow(() -> new EventException("Event not created"));
        log.info("Event successfully created");
        return eventMappingService.toEventWebDto(result);
    }

    @Override
    public EventWebDto updateEvent(EventWebDto eventWebDto, int id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("event not found with id: {}", id);
                    return new EventException("Event not found");
                });
        event.setMessage(eventWebDto.message())
                .setType(eventWebDto.type())
                .setUserId(eventWebDto.userId())
                .setTransactionId(eventWebDto.transactionId());
        Event result = eventRepository.updateEvent(event);
        log.info("Event {} updated", eventWebDto);
        return eventMappingService.toEventWebDto(result);
    }

    @Override
    public void delete(int id) {
        eventRepository.deleteEvent(id);
    }

    @Override
    public EventWebDto getEventWebDtoFromEvent(Optional<Event> event) {
        return mapping.eventToEventDto(event);
    }
}
