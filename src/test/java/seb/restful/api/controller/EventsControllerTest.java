package seb.restful.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import seb.restful.api.model.Event;
import seb.restful.api.model.enums.MessageType;
import seb.restful.api.service.EventServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventsController.class)
class EventsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EventServiceImpl eventService;

    @Autowired
    ObjectMapper objectMapper;

    private final List<Event> events = new ArrayList<>();

    @BeforeEach
    void setUp() {
        events.add(new Event(1,
                LocalDateTime.now(),
                MessageType.DEBUG,
                "event submitted",
                "12345",
                "444-555-666"));
        events.add(new Event(2,
                LocalDateTime.now(),
                MessageType.INFO,
                "event being processed",
                "12346",
                "444-555-333"));
    }

    @Test
    public void getAllEventsTest() throws Exception {
        when(eventService.findAll()).thenReturn(events);

        mockMvc.perform(MockMvcRequestBuilders.get("/events"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("DEBUG"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].message").value("event submitted"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value("12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].transactionId").value("444-555-666"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].type").value("INFO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].message").value("event being processed"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userId").value("12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].transactionId").value("444-555-666"));
    }

    @Test
    public void getEventByIdTest() {

    }

    @Test
    void updateEventTest() {
    }

    @Test
    void deleteEventTest() {
    }
}