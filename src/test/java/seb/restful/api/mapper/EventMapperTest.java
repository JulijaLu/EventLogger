package seb.restful.api.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import seb.restful.api.model.Event;
import seb.restful.api.model.enums.MessageType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class EventMapperTest {

    @Autowired
    private EventMapper eventMapper;

    @Test
    void findAllTest() {
        eventMapper.createEvent(new Event(1, LocalDateTime.now(), MessageType.DEBUG, "event submitted", "12345", "444-555-666"));
        eventMapper.createEvent(new Event(2, LocalDateTime.now(), MessageType.INFO, "event being processed", "12345", "444-555-666"));
        List<Event> events = eventMapper.findAll();
        assertNotNull(events);
        assertEquals(2, events.size());
    }

    @Test
    void findByIdTest() {
        Event event = new Event(1, LocalDateTime.now(), MessageType.DEBUG, "event submitted", "12345", "444-555-666");
        eventMapper.createEvent(event);
        Event foundEvent = eventMapper.findById(event.getId());
        assertNotNull(foundEvent);
        assertEquals(event.getId(), foundEvent.getId());
    }

    @Test
    void createEventTest() {
        Event event = new Event(1, LocalDateTime.now(), MessageType.DEBUG, "event submitted", "12345", "444-555-666");
        List <Event> savedEvents = new ArrayList<>();
        savedEvents.add(event);
        assertNotNull(savedEvents);
        assertEquals(1, savedEvents.size());
        Event createdEvent = savedEvents.get(0);
        assertEquals(1, createdEvent.getId());
        assertEquals(MessageType.DEBUG, createdEvent.getType());
        assertEquals("event submitted", createdEvent.getMessage());
        assertEquals("12345", createdEvent.getUserId());
        assertEquals("444-555-666", createdEvent.getTransactionId());
    }

    @Test
    void updateEventTest() {
        Event event = new Event(1, LocalDateTime.now(), MessageType.DEBUG, "event submitted", "12345", "444-555-666");
        eventMapper.createEvent(event);
        event.setMessage("no code was debugged");
        eventMapper.createEvent(event);
        Event updatedEvent = eventMapper.findById(event.getId());
        assertNotNull(updatedEvent);
        assertEquals("no code was debugged", updatedEvent.getMessage());
    }

    @Test
    void deleteEventTest() {
        Event event = new Event(1, LocalDateTime.now(), MessageType.DEBUG, "event submitted", "12345", "444-555-666");
        eventMapper.createEvent(event);
        eventMapper.deleteEvent(event.getId());
        Event deletedEvent = eventMapper.findById(event.getId());
        assertNull(deletedEvent);
    }
}