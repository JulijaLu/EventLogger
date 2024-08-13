package lt.seb.restful.api.service;


import lt.seb.restful.api.dto.EventWebDto;
import lt.seb.restful.repository.EventRepository;
import lt.seb.restful.model.Event;
import lt.seb.restful.model.enums.MessageType;
import org.aspectj.bridge.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EventsServiceTest {

    @Autowired
    private EventServiceImpl eventService;

    @MockBean
    private EventRepository eventMapper;

    @Test
    public void findEventByIdTest() {
        Event event = new Event()
                .setMessage("hdasdef")
                .setType(MessageType.DEBUG)
                .setUserId(1234)
                .setTransactionId(4555666);
        when(eventMapper.findById(0)).thenReturn(Optional.of(event));
        EventWebDto eventFound = eventService.findById(1);
        assertEquals("hdasdef", eventFound.message());
    }

}
