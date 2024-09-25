package lt.seb.restful.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.seb.restful.api.dto.EventDto;
import lt.seb.restful.api.dto.enums.MessageType;
import lt.seb.restful.mapping.EventMapper;
import lt.seb.restful.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static lt.seb.restful.api.dto.enums.MessageType.DEBUG;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMybatis
@AutoConfigureMockMvc(addFilters = false)
class EventsControllerIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EventRepository eventRepository;
    private List<EventDto> events = new ArrayList<>();

    @Autowired
    private EventMapper eventMapper;

    @Test
    public void getAllEvents_allEventsFound_200() throws Exception {

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/events"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("DEBUG"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].message").value("event pending"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(10101))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].transactionId").value(333555666))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].type").value("INFO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].message").value("event submitted"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userId").value(11111))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].transactionId").value(111555222));
    }

    @Test
    public void getEventById_eventFoundById_200() throws Exception {
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("DEBUG"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("event pending"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(10101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionId").value(333555666));
    }

    @Test
    public void getEventById_eventNotFoundById_404() throws Exception {
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/events/3"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("event not found with id: 3"));
    }

    @Test
    public void createEvent_eventCreated_201() throws Exception {
        // given
        eventRepository.deleteAllEvents();
        EventDto eventDto = new EventDto(MessageType.DEBUG, "event submitted", 11111, 111555222);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("DEBUG"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("event submitted"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(11111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionId").value(111555222));
    }

    @Test
    void updateEvent_eventUpdated_200() throws Exception {
        // given
        EventDto eventDtoToUpdate = new EventDto(DEBUG, "event UPDATED", 12345, 444555666);

        // then
        mockMvc.perform(put("/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDtoToUpdate)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("DEBUG"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("event UPDATED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(12345))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionId").value(444555666));
    }

    @Test
    void updateEvent_eventNotUpdated() throws Exception {
        // given
        EventDto eventDtoToUpdate = new EventDto(DEBUG, "event UPDATED", -111, 444555666);

        // then
        mockMvc.perform(put("/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDtoToUpdate)))
                .andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("JSON parse error: Cannot deserialize value of type `lt.seb.restful.api.dto.enums.MessageType` from String \"DEBUGGGGGGG\": not one of the values accepted for Enum class: [ERROR, DEBUG, WARNING, INFO]"));
    }

    @Test
    void deleteEvent_eventDeleted_204() throws Exception {
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("/events/{2}", 1))
                .andExpect(status().isNoContent());
    }
}