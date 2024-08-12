package lt.seb.restful.api.service;

import lt.seb.restful.api.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Optional<Event> findById(int id);
    List<Event> findAll();
    void createEvent(Event event);
    void updateEvent(Event event);
    void delete(int id);

}
