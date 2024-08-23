package lt.seb.restful.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.seb.restful.api.dto.EventWebDto;
import lt.seb.restful.api.service.EventServiceImpl;
import lt.seb.restful.mapping.EventMappingService;
import lt.seb.restful.model.Event;
import lt.seb.restful.model.enums.MessageType;
import lt.seb.restful.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = EventsController.class)
@Import(EventsController.class)
class EventsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private EventServiceImpl eventService;

    @MockBean
    private EventRepository eventRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final List<EventWebDto> events = new ArrayList<>();

    @Autowired
    private EventMappingService eventMappingService;

    @BeforeEach
    void setUp() {
        events.add(new EventWebDto(
                MessageType.DEBUG,
                "event submitted",
                12345,
                444555666));
        events.add(new EventWebDto(
                MessageType.INFO,
                "event being processed",
                12346,
                444555333));
    }

    @Test
    public void getAllEventsTest() throws Exception {
        when(eventService.findAll()).thenReturn(events);

        mockMvc.perform(MockMvcRequestBuilders.get("/events"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("DEBUG"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].message").value("event submitted"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(12345))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].transactionId").value(444555666))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].type").value("INFO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].message").value("event being processed"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userId").value(12346))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].transactionId").value(444555333));
    }

    @Test
    public void getEventByIdTest() throws Exception {
        Event event = new Event(1, LocalDateTime.now(), MessageType.DEBUG, "event submitted", 11111, 111555222);;
        when(eventRepository.findById(1)).thenReturn(Optional.of(event));

        mockMvc.perform(MockMvcRequestBuilders.get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("DEBUG"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("event submitted"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(11111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionId").value(111555222));
    }

    @Test
    public void createEventTest() throws Exception {
        EventWebDto newEvent = new EventWebDto(MessageType.DEBUG, "event CREATED", 12345, 444555666);
        Event newEvent2 = new Event(1, LocalDateTime.now(), MessageType.DEBUG, "event UPDATED", 12345, 444555666);
        EventWebDto newEventUpdated = new EventWebDto(MessageType.DEBUG, "event UPDATED", 12345, 444555666);
        when(eventService.createEvent(newEventUpdated)).thenReturn(newEventUpdated);

        mockMvc.perform(MockMvcRequestBuilders.post("/events"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("DEBUG"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("event CREATED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(12345))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionId").value(444555666));
    }

    @Test
    void updateEventTest() throws Exception {
        Event originalEvent = new Event(1, LocalDateTime.now(), MessageType.DEBUG, "event submitted", 12345, 444555666);;
        EventWebDto eventWebDtoToUpdate = new EventWebDto(MessageType.DEBUG, "event UPDATED", 12345, 444555666);
        Event updatedEvent = new Event(1, LocalDateTime.now(), MessageType.DEBUG, "event UPDATED", 12345, 444555666);
        when(eventRepository.findById(1)).thenReturn(Optional.of(originalEvent));
        when(eventRepository.updateEvent(originalEvent)).thenReturn(updatedEvent);

        mockMvc.perform(put("/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventWebDtoToUpdate)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("DEBUG"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("event UPDATED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(12345));
    }

    @Test
    void deleteEventTest() throws Exception {
        Event newEvent2 = new Event(1, LocalDateTime.now(), MessageType.DEBUG, "event UPDATED", 12345, 444555666);
        when(eventRepository.findById(1)).thenReturn(Optional.of(newEvent2));
        mockMvc.perform(MockMvcRequestBuilders.delete("/events/{2}", 1))
                .andExpect(status().isNoContent());
        verify(eventRepository).deleteEvent(1);
    }
}