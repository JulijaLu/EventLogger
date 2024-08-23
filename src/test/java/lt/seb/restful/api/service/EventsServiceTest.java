package lt.seb.restful.api.service;

import lt.seb.restful.api.dto.EventWebDto;
import lt.seb.restful.mapping.EventMappingService;
import lt.seb.restful.model.Event;
import lt.seb.restful.model.enums.MessageType;
import lt.seb.restful.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(EventServiceImpl.class)
@Import(EventServiceImpl.class)
public class EventsServiceTest {

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private EventMappingService eventMappingService;

    @Autowired
    private EventService eventService;

    @Test
    public void findAllEventsTest() {
        Event event = new Event()
                .setMessage("hello world")
                .setType(MessageType.DEBUG)
                .setUserId(1234)
                .setTransactionId(4555666);
        Event event2 = new Event()
                .setMessage("hello java")
                .setType(MessageType.INFO)
                .setUserId(1235)
                .setTransactionId(4555777);
        List<Event> eventList = new ArrayList<>();
        List<EventWebDto> eventDtoList = new ArrayList<>();
        eventList.add(event);
        eventList.add(event2);

        EventWebDto eventWebDto = eventMappingService.toEventWebDto(event);
        EventWebDto eventWebDto2 = eventMappingService.toEventWebDto(event2);
        eventDtoList.add(eventWebDto);
        eventDtoList.add(eventWebDto2);

        when(eventRepository.findAll()).thenReturn(eventList);
        when(eventMappingService.eventWebDtoList(eventList)).thenReturn(eventDtoList);
        assertEquals(eventDtoList, eventMappingService.eventWebDtoList(eventList));
        verify(eventRepository, times(1)).findAll();
        verify(eventMappingService, times(1)).eventWebDtoList(eventList);
    }

    @Test
    public void findEventByIdTest() {
        Event event = new Event()
                .setMessage("hello world")
                .setType(MessageType.DEBUG)
                .setUserId(1234)
                .setTransactionId(4555666);
        when(eventRepository.findById(1)).thenReturn(Optional.of(event));
        EventWebDto eventWebDto = eventService.findById(1);
        assertEquals("hello world", eventWebDto.message());
    }

    @Test
    public void createEventTest() {
        EventWebDto eventWebDto = EventWebDto.builder().message("hello java")
                .type(MessageType.DEBUG)
                .userId(1234)
                .transactionId(4555666).build();
        Event event = eventMappingService.toEvent(eventWebDto);
        when(eventRepository.createEvent(event)).thenReturn(1);
    }

    @Test
    public void updateEventTest() {
        int id = 1;
        Event originalEvent = new Event()
                .setMessage("hello world")
                .setType(MessageType.DEBUG)
                .setUserId(1234)
                .setTransactionId(4555666);
        EventWebDto eventWebDto = EventWebDto.builder()
                .message("hello java")
                .type(MessageType.DEBUG)
                .userId(1234)
                .transactionId(4555666)
                .build();
        Event updatedEvent = new Event()
                .setMessage("hello java")
                .setType(MessageType.DEBUG)
                .setUserId(1234)
                .setTransactionId(4555666);

        when(eventRepository.findById(id)).thenReturn(Optional.of(originalEvent));
        when(eventRepository.updateEvent(originalEvent)).thenReturn(updatedEvent);
        when(eventMappingService.toEventWebDto(updatedEvent)).thenReturn(eventWebDto);
        EventWebDto result = eventService.updateEvent(eventWebDto, id);
        assertEquals(eventWebDto, result);
    }

    @Test
    public void deleteEventTest() {
        Event originalEvent = new Event(1, LocalDateTime.now(), MessageType.DEBUG, "event UPDATED", 12345, 444555666);
        when(eventRepository.findById(0)).thenReturn(Optional.of(originalEvent));
        assertEquals(originalEvent, eventRepository.findById(0));
    }
}
