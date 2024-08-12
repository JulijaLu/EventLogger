package lt.seb.restful.api.controller;

import lombok.Data;
import lt.seb.restful.api.model.enums.MessageType;
import lt.seb.restful.api.service.EventServiceImpl;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lt.seb.restful.api.model.Event;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventsController {

    private EventServiceImpl eventService;

    public EventsController(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable("id") int id) {
        return eventService.findById(id).orElse(null);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createEvent(@RequestBody Event event) {
       eventService.createEvent(event);
    }

    @PutMapping("/{id}")
    public void updateEvent(@RequestBody Event newEvent, @PathVariable("id") int id) {
        eventService.findById(id)
                .ifPresent(event -> {
                    event.setType(newEvent.getType());
                    event.setMessage(newEvent.getMessage());
                    event.setUserId(newEvent.getUserId());
                    event.setTransactionId(newEvent.getTransactionId());
                    eventService.updateEvent(event);
                });
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable("id") int id) {
        eventService.delete(id);
    }

}
