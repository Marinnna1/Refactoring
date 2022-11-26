package ru.javaschool.JavaSchoolBackend1.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.javaschool.JavaSchoolBackend1.controller.MessageController;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventsServiceTest {

    @Mock
    MessageController messageController;

    @InjectMocks
    EventsService eventsService;


    @Test
    @DisplayName("Send custom message test")
    void sendTest() throws IOException {
        eventsService.send(null);
        verify(messageController, times(1)).recMessage(null);

    }

}
