package seb.restful.api.service;

import seb.restful.api.model.Event;

import java.util.List;

public interface EventService {
    Event findById(int id);
    List<Event> findAll();
    void createEvent(Event event);
    void updateEvent(Event event);
    void delete(int id);

}
