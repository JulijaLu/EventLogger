package lt.seb.restful.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.seb.restful.api.dto.EventDto;
import lt.seb.restful.api.dto.enums.MessageType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static lt.seb.restful.api.dto.enums.MessageType.DEBUG;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class EventsControllerIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllEvents_allEventsFound() throws Exception {
        // when&then
        mockMvc.perform(MockMvcRequestBuilders.get("/events/all"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("DEBUG"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].message").value("event pending"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(10101))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].transactionId").value(333555666))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].type").value("INFO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].message").value("event submitted"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userId").value(10102))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].transactionId").value(333555667));
    }

    @Test
    void getEventById_eventFoundById() throws Exception {
        // when&then
        mockMvc.perform(MockMvcRequestBuilders.get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("DEBUG"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("event pending"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(10101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionId").value(333555666));
    }

    @Test
    void getEventById_eventNotFoundById() throws Exception {
        // when&then
        mockMvc.perform(MockMvcRequestBuilders.get("/events/3"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("event not found with id: 3"));
    }

    @Test
    void createEvent_eventCreated() throws Exception {
        // given
        EventDto eventDto = new EventDto(MessageType.DEBUG, "event submitted", 11111, 111555222);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void updateEvent_eventUpdated() throws Exception {
        // given
        EventDto eventDtoToUpdate = new EventDto(DEBUG, "event UPDATED", 12345, 444555666);

        // when&then
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
    void updateEvent_evenNotUpdated() throws Exception {
        // given
        EventDto eventDtoToUpdate = new EventDto(DEBUG, "event UPDATED", 12345, 444555666);

        // when&then
        mockMvc.perform(put("/events/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDtoToUpdate)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteEvent_eventDeleted() throws Exception {
        // when&then
        mockMvc.perform(MockMvcRequestBuilders.delete("/events/{2}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteEvent_eventNotDeleted() throws Exception {
        // when&then
        mockMvc.perform(MockMvcRequestBuilders.delete("/events/{5}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void filterEvent_eventFiltered() throws Exception {
        // when&then
        mockMvc.perform(MockMvcRequestBuilders.get("/events/filter")
                        .param("type", "DEBUG")
                        .param("message", "event pending")
                        .param("userId", "10101")
                        .param("transactionId", "333555666"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("DEBUG"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].message").value("event pending"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(10101))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].transactionId").value(333555666));
    }
}