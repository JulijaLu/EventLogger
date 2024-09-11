package lt.seb.restful.api.controller;

import lombok.AllArgsConstructor;
import lt.seb.restful.api.dto.EventDto;
import lt.seb.restful.api.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/events")
public class EventsController {

    private final EventService eventService;

    @GetMapping
    public List<EventDto> getAllEvents() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public EventDto getEventById(@PathVariable("id") int id) {
        return eventService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EventDto createEvent(@RequestBody EventDto event) {
       return eventService.createEvent(event);
    }

    @PutMapping("/{id}")
    public EventDto updateEvent(@RequestBody EventDto event, @PathVariable("id") int id) {
        return eventService.updateEvent(event, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable("id") int id) {
        eventService.delete(id);
    }

    @GetMapping("/?fieldName={fieldName}")
    public List<EventDto> filterEvents(@RequestParam("fieldName") String fieldName) {
        return eventService.filterEvents(fieldName);
    }
}
