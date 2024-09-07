package lt.seb.restful.api.service;

import lt.seb.restful.api.dto.EventWebDto;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventsServiceTest {

    @Mock
    private EventRepository eventRepository;

    private EventService eventService;

    private EventMapper eventMapper = new EventMapperImpl();

    List<Event> eventList = new ArrayList<>();
    List<EventWebDto> eventDtoList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        eventService = new EventServiceImpl(eventMapper, eventRepository);
        eventDtoList = List.of(
                EventWebDto.builder()
                        .message("hello world")
                        .type(MessageType.DEBUG)
                        .userId(1234)
                        .transactionId(4555666)
                        .build(),
                EventWebDto.builder()
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
    public void findAllEventsTest() {
        // given
        when(eventRepository.findAll()).thenReturn(eventList);

        // when
        List<EventWebDto> result = eventService.findAll();

        // then
        assertEquals(eventDtoList, result);
    }

    @Test
    public void findEventByIdTest_eventIsFound() {
        // given
        Event event = eventList.get(0);
        when(eventRepository.findById(1)).thenReturn(Optional.of(event));

        // when
        EventWebDto eventWebDto = eventService.findById(1);

        // then
        assertEquals("hello world", eventWebDto.message());
        assertEquals(1234, eventWebDto.userId());
    }

    @Test
    public void findEventByIdTest_eventIsNotFound() {
        // when
        final Executable executable = () -> eventService.findById(1);

        // then
        assertThrows(EventNotFoundException.class, executable, "Event not found");
    }

    @Test
    public void createEventTest_eventCreated() {
        // given
        Optional<Event> event = Optional.of(eventList.get(0));
        EventWebDto eventWebDto = eventDtoList.get(0);
        when(eventRepository.createEvent(any())).thenReturn(1);
        when(eventRepository.findById(1)).thenReturn(event);

        // when
        EventWebDto createdEvent = eventService.createEvent(eventWebDto);

        // then
        assertEquals("hello world", createdEvent.message());
    }

    @Test
    public void createEventTest_eventNotCreated() {
        // when
        final Executable executable = () -> eventService.findById(1);

        // TODO: fix executable
        // then
        assertThrows(EventNotFoundException.class, executable,"Event not created");
    }

    @Test
    public void updateEventTest_eventUpdated() {
        // given
        int id = 1;
        Event originalEvent = eventList.get(0);
        Optional<Event> updatedEvent = Optional.of(eventList.get(id));
        EventWebDto eventWebDto = eventDtoList.get(id);
        when(eventRepository.updateEvent(originalEvent)).thenReturn(id);
        when(eventRepository.findById(id)).thenReturn(Optional.of(originalEvent), updatedEvent);

        // when
        EventWebDto result = eventService.updateEvent(eventWebDto, id);

        //then
        assertEquals(eventWebDto, result);
    }

    @Test
    public void updateEventTest_eventNotUpdated() {
        // when
        final Executable executable = () -> eventService.updateEvent(eventDtoList.get(0), 1);

        //then
        assertThrows(EventNotFoundException.class, executable,"event not updated");
    }

    @Test
    public void deleteEventTest() {
        // given
        int id = 1;

        // when
        eventService.delete(id);

        // then
        verify(eventRepository).deleteEvent(id);
    }
}
