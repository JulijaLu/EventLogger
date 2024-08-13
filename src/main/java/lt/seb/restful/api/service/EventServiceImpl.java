package lt.seb.restful.api.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lt.seb.restful.api.dto.EventWebDto;
import lt.seb.restful.exception.EventException;
import lt.seb.restful.mapping.EventMappingService;
import lt.seb.restful.model.Event;
import lt.seb.restful.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private EventMappingService eventMappingService;

    @Override
    public EventWebDto findById(int id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventException("Event not found"));
        return eventMappingService.toEventWebDto(event);
    }

    @Override
    public List<EventWebDto> findAll() {
        return eventMappingService.eventWebDtoList(eventRepository.findAll());
    }

    @Override
    public EventWebDto createEvent(EventWebDto eventWebDto) {
        Event event = eventMappingService.toEvent(eventWebDto);
        Event result = eventRepository.createEvent(event);
        return eventMappingService.toEventWebDto(result);
    }

    @Override
    public EventWebDto updateEvent(EventWebDto eventWebDto, int id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventException("Event not found"));
        event.setMessage(eventWebDto.message())
                .setType(eventWebDto.type())
                .setUserId(eventWebDto.userId())
                .setTransactionId(eventWebDto.transactionId());
        Event result = eventRepository.updateEvent(event);
        return eventMappingService.toEventWebDto(result);
    }

    @Override
    public void delete(int id) {
        eventRepository.deleteEvent(id);
    }

}
