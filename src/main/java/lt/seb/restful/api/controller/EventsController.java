package lt.seb.restful.api.controller;

import lombok.AllArgsConstructor;
import lt.seb.restful.api.dto.EventWebDto;
import lt.seb.restful.api.service.EventService;
import lt.seb.restful.model.Event;
import lt.seb.restful.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/events")
public class EventsController {

    private final EventService eventService;

    @GetMapping
    public List<EventWebDto> getAllEvents() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public EventWebDto getEventById(@PathVariable("id") int id) {
        return eventService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EventWebDto createEvent(@RequestBody EventWebDto event) {
       return eventService.createEvent(event);
    }

    @PutMapping("/{id}")
    public EventWebDto updateEvent(@RequestBody EventWebDto event, @PathVariable("id") int id) {
        return eventService.updateEvent(event, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable("id") int id) {
        eventService.delete(id);
    }

}
