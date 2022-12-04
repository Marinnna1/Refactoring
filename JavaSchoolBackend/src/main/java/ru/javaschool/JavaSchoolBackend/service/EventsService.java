package ru.javaschool.JavaSchoolBackend.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.javaschool.JavaSchoolBackend.dao.EventsDao;
import ru.javaschool.JavaSchoolBackend.dto.EventDto;
import ru.javaschool.JavaSchoolBackend.dto.MessageDto;
import ru.javaschool.JavaSchoolBackend.email.EmailMessage;
import ru.javaschool.JavaSchoolBackend.entity.Appointment;
import ru.javaschool.JavaSchoolBackend.entity.Event;
import ru.javaschool.JavaSchoolBackend.mq.CustomMessage;
import ru.javaschool.JavaSchoolBackend.mq.MessagePublisher;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EventsService {

    private final EventsDao eventsDao;

    private final MessagePublisher messagePublisher;

    private final EmailService emailService;

    private static Logger LOGGER = LoggerFactory.getLogger(AppointmentsService.class.getName());



    public List<EventDto> getAll(Integer pageNumber, String status, String filter) {
        List<Event> events = eventsDao.findAll(pageNumber, status, filter);
        List<EventDto> eventDtos = new ArrayList<>();
        EventDto currentEventDto = new EventDto();
        int iteration = 0;

        for(Event event: events) {
            currentEventDto.setId(iteration);
            currentEventDto.setPatientName(event.getAppointment().getPatient().getName());
            currentEventDto.setDate(event.getDate().toString());
            currentEventDto.setStatus(event.getStatus());
            currentEventDto.setTreatmentName(event.getAppointment().getTreatment().getName());
            currentEventDto.setTreatmentType(event.getAppointment().getTreatment().getType());
            currentEventDto.setReason(event.getReason());
            eventDtos.add(currentEventDto);
            currentEventDto = new EventDto();
            iteration++;
        }
        return eventDtos;
    }

    public Long getEventsCount(EventDto eventDto) {
        return eventsDao.getEventsCount(eventDto.getStatus(), eventDto.getFilter());
    }

    public MessageDto editStatus(EventDto eventDto) {
        if(eventDto.getStatus() != null) {
            if ((eventDto.getStatus().equals("Cancelled") || eventDto.getStatus().equals("Done"))
                    && eventDto.getOldStatus().equals("Planned")) {
                MessageDto messageDto = eventsDao.changeStatus(eventDto);
                if(messageDto.getMessage().equals("Status successfully changed")) {
                    CustomMessage customMessage = new CustomMessage();
                    customMessage.setMessage("update");
                    messagePublisher.publishMessage(customMessage);
                }
                return messageDto;
            }
        }
        LOGGER.error("Something went wrong, can't change status in event");
        return new MessageDto("Something went wrong, can't change status");
    }



    void addEvents(Appointment appointment) {
        eventsDao.generateEvents(appointment);
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("update");
        messagePublisher.publishMessage(customMessage);
    }

    void editEvents(Appointment appointment) {
        eventsDao.deleteEvents(appointment);
        eventsDao.generateEvents(appointment);
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("update");
        messagePublisher.publishMessage(customMessage);
    }



    @Scheduled(cron = "0 0 7 * * *")
    private void sendMessageForTodayEvents() {
        LOGGER.info("schedule method - send letters to emails");
        List<Event> events = eventsDao.findTodayEvents();
        for(Event event : events) {
            emailService.sendMessage(event.getAppointment().getPatient().getEmail(),
                    "Today event", new EmailMessage().getMessageForTodayEvent(event));
        }
    }
}

