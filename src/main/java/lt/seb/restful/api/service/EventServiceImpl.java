package lt.seb.restful.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import lt.seb.restful.api.dto.EventDto;
import lt.seb.restful.api.dto.enums.MessageType;
import lt.seb.restful.exception.EventNotFoundException;
import lt.seb.restful.mapping.EventMapper;
import lt.seb.restful.model.Event;
import lt.seb.restful.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    @Override
    public List<EventDto> findAll() {
        List<Event> events = eventRepository.findAll();
        return eventMapper.convertEventListToEventDtoList(events);
    }

    @Override
    public EventDto findById(int id) {
        Event event = findEventOrThrowException(id);
        return eventMapper.convertEventToEventDto(event);
    }

    @Override
    public EventDto findByUserId(int userId) {
        Event event = eventRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new EventNotFoundException("event not found with id: " + userId));
        return eventMapper.convertEventToEventDto(event);
    }

    @Override
    public EventDto createEvent(EventDto eventDto) {
        Event event = eventMapper.convertEventDtoToEvent(eventDto);
        eventRepository.createEvent(event);
        Event result = findEventOrThrowException(event.getId());
        return eventMapper.convertEventToEventDto(result);
    }

    @Override
    public EventDto updateEvent(EventDto eventDto, int id) {
        Event event = findEventOrThrowException(id);
        updateEventFields(event, eventDto);
        eventRepository.updateEvent(event);
        return eventMapper.convertEventToEventDto(event);
    }

    @Override
    public void delete(int id) {
        eventRepository.deleteEvent(id);
    }

    @Override
    public List<EventDto> filterEvents(
            MessageType type, String message, Integer userId, Integer transactionId)
    {
        String messageType = eventMapper.messageTypeToString(type);
        List<Event> events = eventRepository.filterEvents(messageType, message, userId, transactionId);
        return eventMapper.convertEventListToEventDtoList(events);
    }

    public void updateEventFields(Event event, EventDto eventDto) {
        event.setMessage(eventDto.message())
                .setType(eventDto.type().toString())
                .setUserId(eventDto.userId())
                .setTransactionId(eventDto.transactionId());
    }

    public Event findEventOrThrowException(int id) {
        return eventRepository.findById(id)
                .orElseThrow(() ->
                        new EventNotFoundException("event not found with id: " + id));
    }
}
