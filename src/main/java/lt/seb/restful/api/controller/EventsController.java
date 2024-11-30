package lt.seb.restful.api.controller;

import lombok.RequiredArgsConstructor;
import lt.seb.restful.api.dto.EventDto;
import lt.seb.restful.api.dto.enums.MessageType;
import lt.seb.restful.api.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/events")
public class EventsController {

    private final EventService eventService;

    @GetMapping("/all")
    public List<EventDto> getAllEvents() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public EventDto getEventById(@PathVariable("id") int id) {
        return eventService.findById(id);
    }

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
        EventDto createdEvent = eventService.createEvent(eventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@RequestBody EventDto event, @PathVariable("id") int id) {
        EventDto updatedEvent = eventService.updateEvent(event, id);
        return ResponseEntity.ok(updatedEvent);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable("id") int id) {
        eventService.delete(id);
    }

    @GetMapping("/filter")
    public List<EventDto> filterEvents(
            @RequestParam(value = "type", required = false) MessageType type,
            @RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "transactionId", required = false) Integer transactionId) {
        return eventService.filterEvents(type, message, userId, transactionId);
    }
}
