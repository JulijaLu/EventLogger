package lt.seb.restful.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.seb.restful.api.dto.EventDto;
import lt.seb.restful.api.service.EventServiceImpl;
import lt.seb.restful.mapping.EventMapper;
import lt.seb.restful.mapping.EventMapperImpl;
import lt.seb.restful.model.Event;
import lt.seb.restful.api.dto.enums.MessageType;
import lt.seb.restful.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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
@SpringBootTest
class EventsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private EventServiceImpl eventService;

    @MockBean
    private EventRepository eventRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<EventDto> events = new ArrayList<>();

    @Autowired
    private EventMapper eventMapper;

    @BeforeEach
    void setUp() {
        events.add(new EventDto(
                MessageType.DEBUG,
                "event submitted",
                12345,
                444555666));
        events.add(new EventDto(
                MessageType.INFO,
                "event being processed",
                12346,
                444555333));
    }

    @Test
    public void getAllEventsTest() throws Exception {
        // given
        when(eventService.findAll()).thenReturn(events);

        // then
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
        // given
        Event event = new Event(1, LocalDateTime.now(), "DEBUG", "event submitted", 11111, 111555222);
        when(eventRepository.findById(1)).thenReturn(Optional.of(event));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("DEBUG"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("event submitted"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(11111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionId").value(111555222));
    }


    // is this method correct?
    @Test
    public void createEventTest() throws Exception {
        // given
        Event event = new Event();
        event.setType("DEBUG").setMessage("event submitted").setUserId(11111).setTransactionId(111555222);
        int id = event.getId();

        when(eventRepository.createEvent(event)).thenReturn(id);
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("DEBUG"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("event submitted"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(11111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionId").value(111555222));
    }

    @Test
    void updateEventTest() throws Exception {
        // given
        Event originalEvent = new Event(1, LocalDateTime.now(), "DEBUG", "event submitted", 12345, 444555666);;
        EventDto eventDtoToUpdate = new EventDto(MessageType.DEBUG, "event UPDATED", 12345, 444555666);

        when(eventRepository.findById(1)).thenReturn(Optional.of(originalEvent));
        when(eventRepository.updateEvent(originalEvent)).thenReturn(1);

        // then
        mockMvc.perform(put("/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDtoToUpdate)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("DEBUG"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("event UPDATED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(12345));
    }

    @Test
    void deleteEventTest() throws Exception {
        // given
        Event newEvent2 = new Event(1, LocalDateTime.now(), "DEBUG", "event UPDATED", 12345, 444555666);
        when(eventRepository.findById(1)).thenReturn(Optional.of(newEvent2));

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("/events/{2}", 1))
                .andExpect(status().isNoContent());
        verify(eventRepository).deleteEvent(1);
    }
}