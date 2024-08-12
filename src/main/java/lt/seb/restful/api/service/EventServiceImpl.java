package lt.seb.restful.api.service;

import org.springframework.stereotype.Service;
import lt.seb.restful.api.mapper.EventMapper;
import lt.seb.restful.api.model.Event;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private EventMapper eventMapper;

    public EventServiceImpl(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    @Override
    public Optional <Event> findById(int id) {
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
    public void updateEvent(Event event) {
        eventMapper.updateEvent(event);
    }

    @Override
    public void delete(int id) {
        eventMapper.deleteEvent(id);
    }

}
