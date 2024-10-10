package lt.seb.restful.api.service;

import lt.seb.restful.api.dto.EventDto;
import lt.seb.restful.api.dto.enums.MessageType;
import lt.seb.restful.exception.EventNotFoundException;
import lt.seb.restful.mapping.EventMapper;
import lt.seb.restful.mapping.EventMapperImpl;
import lt.seb.restful.model.Event;
import lt.seb.restful.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventsServiceTest {

    @Mock
    private EventRepository eventRepository;

    private EventService eventService;

    private EventMapper eventMapper = new EventMapperImpl();

    List<Event> eventList = new ArrayList<>();
    List<EventDto> eventDtoList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        eventService = new EventServiceImpl(eventMapper, eventRepository);
        eventDtoList = List.of(
                EventDto.builder()
                        .message("hello world")
                        .type(MessageType.DEBUG)
                        .userId(1234)
                        .transactionId(4555666)
                        .build(),
                EventDto.builder()
                        .message("hello java")
                        .type(MessageType.INFO)
                        .userId(4411)
                        .transactionId(3335666)
                        .build());

        eventList = List.of(
                new Event()
                        .setMessage("hello world")
                        .setType("DEBUG")
                        .setUserId(1234)
                        .setTransactionId(4555666),
                new Event()
                        .setMessage("hello java")
                        .setType("INFO")
                        .setUserId(4411)
                        .setTransactionId(3335666));
    }

    @Test
    void findAllEvents_eventsFound() {
        // given
        when(eventRepository.findAll()).thenReturn(eventList);

        // when
        List<EventDto> result = eventService.findAll();

        // then
        assertEquals(eventDtoList, result);
    }

    @Test
    void findEventById_eventIsFound() {
        // given
        Event event = eventList.get(0);
        when(eventRepository.findById(1)).thenReturn(Optional.of(event));

        // when
        EventDto eventDto = eventService.findById(1);

        // then
        assertEquals("hello world", eventDto.message());
        assertEquals(1234, eventDto.userId());
    }

    @Test
    void findEventById_eventIsNotFound() {
        // when
        final Executable executable = () -> eventService.findById(1);

        // then
        assertThrows(EventNotFoundException.class, executable, "Event not found");
    }

    @Test
    void createEvent_eventCreated() {
        // given
        Optional<Event> event = Optional.of(eventList.get(0));
        EventDto eventDto = eventDtoList.get(0);
        when(eventRepository.createEvent(any())).thenReturn(1);
        when(eventRepository.findById(1)).thenReturn(event);

        // when
        EventDto createdEvent = eventService.createEvent(eventDto);

        // then
        assertEquals("hello world", createdEvent.message());
    }

    @Test
    void createEvent_eventNotCreated() {
        // given
        Event event = eventList.get(0);
        EventDto eventDto = eventDtoList.get(0);
        when(eventRepository.createEvent(event)).thenReturn(1);

        // when
        final Executable executable = () -> eventService.createEvent(eventDto);

        // then
        assertThrows(EventNotFoundException.class, executable,"Event not created");
    }

    @Test
    void updateEvent_eventUpdated() {
        // given
        int id = 1;
        Event originalEvent = eventList.get(0);
        Optional<Event> updatedEvent = Optional.of(eventList.get(id));
        EventDto eventDto = eventDtoList.get(id);
        when(eventRepository.updateEvent(originalEvent)).thenReturn(id);
        when(eventRepository.findById(id)).thenReturn(Optional.of(originalEvent), updatedEvent);

        // when
        EventDto result = eventService.updateEvent(eventDto, id);

        //then
        assertEquals(eventDto, result);
    }

    @Test
    void updateEvent_eventNotUpdated() {
        //given
        int id = 55;
        EventDto eventDto = eventDtoList.get(0);
        when(eventRepository.findById(id)).thenReturn(Optional.empty());
        // when
        final Executable executable = () -> eventService.updateEvent(eventDto, id);

        //then
        assertThrows(EventNotFoundException.class, executable,"event not updated");
    }

    @Test
    void deleteEvent() {
        // given
        int id = 1;

        // when
        eventService.delete(id);

        // then
        verify(eventRepository).deleteEvent(id);
    }
}
