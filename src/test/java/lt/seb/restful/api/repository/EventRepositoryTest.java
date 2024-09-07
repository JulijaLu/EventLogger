package lt.seb.restful.api.repository;

import lt.seb.restful.model.Event;
import lt.seb.restful.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void findAll() {
        // when
        final List<Event> result = eventRepository.findAll();

        // then
        assertThat(result).hasSize(2);
    }




//    @Autowired
//    private EventRepository eventMapper;
//
//    private final List<Event> events = new ArrayList<>();
//
//    @BeforeEach
//    void setUp() {
//        events.add(new Event(1,
//                LocalDateTime.now(),
//                "DEBUG",
//                "event submitted",
//                12345,
//                444555666));
//        events.add(new Event(2,
//                LocalDateTime.now(),
//                "INFO",
//                "event being processed",
//                12346,
//                444555333));
//    }
//
//    @Test
//    void findAllTest() {
//        eventMapper.createEvent(events.get(0));
//        eventMapper.createEvent(events.get(1));
//        List<Event> events = eventMapper.findAll();
//        assertNotNull(events);
//        assertEquals(2, events.size());
//    }
//
//    @Test
//    void findByIdTest() {
//        Event event = new Event(1, LocalDateTime.now(), "DEBUG", "event submitted", 12345, 444555666);
//        Event foundEventOptional = eventMapper.findById(event.getId()).orElse(null);
//        assertNotNull(foundEventOptional);
//        assertEquals(event.getId(), foundEventOptional.getId());
//    }
//
//    @Test
//    void createEventTest() {
//        Event event = events.get(0);
//        List <Event> savedEvents = new ArrayList<>();
//        savedEvents.add(event);
//        assertNotNull(savedEvents);
//        assertEquals(1, savedEvents.size());
//        Event createdEvent = savedEvents.get(0);
//        assertEquals(1, createdEvent.getId());
//        assertEquals(MessageType.DEBUG, createdEvent.getType());
//        assertEquals("event submitted", createdEvent.getMessage());
//        assertEquals(12345, createdEvent.getUserId());
//        assertEquals(444555666, createdEvent.getTransactionId());
//    }
//
//    @Test
//    void updateEventTest() {
//        Event event = events.get(1);
////        Event updatedEvent = eventMapper.findById(event.getId()).orElse(null);
//        Event eventToBeMerged = events.get(0);
////        eventMapper.updateEvent(eventToBeMerged, 2);
//        assertNotNull(eventToBeMerged);
//        assertEquals("no code was debugged", event.getMessage());
//    }
//
//    @Test
//    void deleteEventTest() {
//        Event event = events.get(1);
//        eventMapper.deleteEvent(event.getId());
//        Event deletedEvent = eventMapper.findById(event.getId()).orElse(null);
//        assertNull(deletedEvent);
//    }
}