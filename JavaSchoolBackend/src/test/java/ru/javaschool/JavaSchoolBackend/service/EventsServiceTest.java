package ru.javaschool.JavaSchoolBackend.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.javaschool.JavaSchoolBackend.dao.EventsDao;
import ru.javaschool.JavaSchoolBackend.dto.EventDto;
import ru.javaschool.JavaSchoolBackend.dto.MessageDto;
import ru.javaschool.JavaSchoolBackend.entity.Appointment;
import ru.javaschool.JavaSchoolBackend.mq.CustomMessage;
import ru.javaschool.JavaSchoolBackend.mq.MessagePublisher;

import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class EventsServiceTest {

    @Mock
    EventsDao eventsDao;

    @Mock
    MessagePublisher messagePublisher;

    @InjectMocks
    EventsService eventsService;

    @Test
    @DisplayName("Test get all events")
    void getAllTest() {
        eventsService.getAll(null, null, null);
        verify(eventsDao, times(1)).findAll(null, null, null);
    }


    @Test
    @DisplayName("Test get events count")
    void getEventsCountTest() {
        eventsService.getEventsCount(new EventDto());
        verify(eventsDao, times(1)).getEventsCount(null, null);
    }


    @Test
    @DisplayName("Test edit status")
    void editStatusTest() {
        EventDto eventDto = new EventDto();
        eventDto.setStatus("Done");
        eventDto.setOldStatus("Planned");
        when(eventsDao.changeStatus(eventDto))
                .thenReturn(new MessageDto("Status successfully changed"));
        eventsService.editStatus(eventDto);
        verify(eventsDao, times(1)).changeStatus(eventDto);
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("update");
        verify(messagePublisher, times(1)).publishMessage(customMessage);
    }


    @Test
    @DisplayName("Test add events")
    void addEventsTest() {
        Appointment appointment = new Appointment();
        eventsService.addEvents(appointment);
        verify(eventsDao, times(1)).generateEvents(appointment);
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("update");
        verify(messagePublisher, times(1)).publishMessage(customMessage);
    }

}
