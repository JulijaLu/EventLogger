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
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void findAllEvents_eventsFound() {
        // when
        final List<Event> result = eventRepository.findAll();

        // then
        assertThat(result).hasSize(2);
    }

    @Test
    void findAllEvents_notFound() {
        // when
        eventRepository.deleteAllEvents();
        final List<Event> result = eventRepository.findAll();

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void findEventById_eventFound() {
        // when
        int id = 1;
        Optional<Event> event = eventRepository.findById(id);

        // then
        assertThat(event.get().getMessage()).isEqualTo("event pending");
    }

    @Test
    void findEventById_eventNotFound() {
        // when
        eventRepository.deleteAllEvents();
        Optional<Event> event = eventRepository.findById(1);

        // then
        assertThat(event).isNotPresent();
    }

    @Test
    void createEvent_eventCreated() {
        // given
        Event event = new Event(
                1, LocalDateTime.now(), "DEBUG", "event submitted", 11111, 111555222
        );

        // when
        int id = eventRepository.createEvent(event);

        // then
        assertThat(id).isEqualTo(1);
    }

    @Test
    void eventNotCreated() {
        // given
        Event event = new Event(
                0, LocalDateTime.now(), "something", "event submitted", 00, 111555222
        );

        // when
        int id = eventRepository.createEvent(event);

        // then
        assertThat(id).isNotEqualTo(0);
    }

    @Test
    void updateEvent() {
        // given
        Event event = new Event(
                1, LocalDateTime.now(), "DEBUG", "event submitted", 11111, 111555222);

        // when
        eventRepository.updateEvent(event);

        // then
        assertThat(eventRepository.findById(1).get().getMessage()).isEqualTo("event submitted");
    }

    @Test
    void eventNotUpdated() {
        // given
        Event event = new Event(
                5, LocalDateTime.now(), "DEBUG", "event submitted", 11111, 111555222
        );

        // when
        int id = eventRepository.updateEvent(event);

        // then
        assertThat(id).isEqualTo(0);
    }

    @Test
    void deleteEvent_eventDeleted() {
        // when
        eventRepository.deleteEvent(1);

        // then
        assertThat(eventRepository.findById(1)).isNotPresent();
    }

    @Test
    void eventNotDeleted() {
        // when
        eventRepository.deleteEvent(5);

        // then
        assertThat(eventRepository.findById(5)).isNotPresent();
    }

    @Test
    void filterEvents_filteredByType() {
        // when
        List<Event> events = eventRepository.filterEvents(
                "DEBUG", null, 10101, 333555666
        );

        //then
        assertThat(events).hasSize(1);
        assertThat(events).extracting(Event::getType).contains("DEBUG");
    }

    @Test
    void filterEvents_filteredByMessage() {
        // when
        List<Event> events = eventRepository.filterEvents(
                null, "submit", 10102, 333555667
        );

        //then
        assertThat(events).hasSize(1);
        assertThat(events).extracting(Event::getMessage).contains("event submitted");
    }
}