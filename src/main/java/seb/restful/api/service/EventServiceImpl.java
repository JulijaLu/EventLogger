package seb.restful.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seb.restful.api.mapper.EventMapper;
import seb.restful.api.model.Event;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventMapper eventMapper;

    @Override
    public Event findById(int id) {
        return eventMapper.findById(id);
    }

    @Override
    public List<Event> findAll() {
        return eventMapper.findAll();
    }

    @Override
    public void createEvent(Event event) {
        eventMapper.createEvent(event);
    }

    @Override
    public void updateEvent(int id) {
        eventMapper.updateEvent(id);
    }

    @Override
    public void delete(int id) {
        eventMapper.deleteEvent(id);
    }

}
