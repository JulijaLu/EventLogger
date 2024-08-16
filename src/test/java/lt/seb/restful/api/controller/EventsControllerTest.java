package lt.seb.restful.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.seb.restful.api.dto.EventWebDto;
import lt.seb.restful.api.service.EventServiceImpl;
import lt.seb.restful.model.Event;
import lt.seb.restful.model.enums.MessageType;
import lt.seb.restful.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMybatis
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(EventsController.class)
class EventsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EventServiceImpl eventService;

    @MockBean
    EventRepository eventRepository;

    @Autowired
    ObjectMapper objectMapper;

    private final List<EventWebDto> events = new ArrayList<>();

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
        Event event = new Event(1, LocalDateTime.now(), MessageType.DEBUG, "event submitted", 12345, 444555666);;
        when(eventRepository.findById(1)).thenReturn(Optional.of(event));
        when(eventService.findById(1)).thenCallRealMethod();

        mockMvc.perform(MockMvcRequestBuilders.get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("DEBUG"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("event pending"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(11111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionId").value(111555222));
    }

    @Test
    public void createEventTest() throws Exception {
        EventWebDto event = EventWebDto.builder().build();
        doThrow(new RuntimeException()).when(eventService).createEvent(event);
        
        mockMvc.perform(MockMvcRequestBuilders.get(""));
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
        mockMvc.perform(delete("/events/2"))
                .andExpect(status().isNoContent());
    }
}