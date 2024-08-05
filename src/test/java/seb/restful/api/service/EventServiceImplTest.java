package seb.restful.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import seb.restful.api.mapper.EventMapper;
import seb.restful.api.model.Event;
import seb.restful.api.model.enums.MessageType;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class EventServiceImplTest {

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventMapper eventMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
//
//    @Test
//    void findByIdTest() {
//        Event event = new Event(1, LocalDateTime.now(), MessageType.DEBUG, "event submitted", "12345", "444-555-666");
//        when(eventMapper.findById(1)).thenReturn(Optional.of(event));
//        Event eventFound = eventService.findById(1);
//        assertEquals("12345", eventFound.getUserId());
//    }
}