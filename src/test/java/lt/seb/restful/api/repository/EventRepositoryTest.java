package lt.seb.restful.api.repository;

import lt.seb.restful.model.Event;
import lt.seb.restful.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Test
    void findAll_notFound() {
        // given
        eventRepository.deleteAllEvents();

        // when
        final List<Event> result = eventRepository.findAll();

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void findEventByIdTest() {
        // when
        int id = 1;
        Optional<Event> event = eventRepository.findById(id);

        // then
        assertThat(event.get().getMessage()).isEqualTo("event pending");
    }

    @Test
    void findEventByIdTest_eventNotFound() {
        // given
        eventRepository.deleteAllEvents();

        // when
        int id = 1;
        Optional<Event> event = eventRepository.findById(id);

        // then
        assertThat(event.isEmpty());
    }

    @Test
    // when
    void createEventTest() {
        // given
        Event event = new Event(1, LocalDateTime.now(), "DEBUG", "event submitted", 11111, 111555222);
    int id = eventRepository.createEvent(event);

    // then
    assertThat(event.getId()).isEqualTo(1);
    }

    @Test
    void createEventTest_eventNotCreated() {

        Event event = new Event(0, LocalDateTime.now(), "DEBUG", "event submitted", 11111, 111555222);
        int id = eventRepository.createEvent(event);

        // then
        assertThat(id).isEqualTo(1);
    }

    @Test
    void updateEventTest() {
        Event event2 = new Event(1, LocalDateTime.now(), "DEBUG", "event submitted", 11111, 111555222);
        int id = eventRepository.createEvent(event2);

        assertThat(id).isEqualTo(event2);
    }

    @Test
    void deleteEventTest() {
        // when
        Optional<Event> event = eventRepository.findById(1);
        eventRepository.deleteEvent(event.get().getId());

        // then
        assertThat(event.isEmpty());
    }
}