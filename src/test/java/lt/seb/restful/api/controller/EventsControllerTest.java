package lt.seb.restful.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lt.seb.restful.api.exception.EventRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import lt.seb.restful.api.model.Event;
import lt.seb.restful.api.model.enums.MessageType;
import lt.seb.restful.api.service.EventServiceImpl;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lt.seb.restful.api.model.enums.MessageType.INFO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMybatis
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
        events.add(new Event(
                MessageType.DEBUG,
                "event submitted",
                12345,
                444555666));
        events.add(new Event(
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
//
//    @Test
//    public void getEventByIdTest() throws Exception {
//        when(eventService.findById(1)).thenReturn(Optional.of(event));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/events/2"))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("DEBUG"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("event pending"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(11111))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionId").value(111555222));
//    }

    @Test
    void updateEventTest() throws Exception {
        Event eventToBeUpdated = events.get(1);
        mockMvc.perform(put("/events/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteEventTest() throws Exception {
        mockMvc.perform(delete("/events/2"))
                .andExpect(status().isNoContent());
    }
}