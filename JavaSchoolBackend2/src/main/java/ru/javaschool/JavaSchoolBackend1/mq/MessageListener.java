package ru.javaschool.JavaSchoolBackend1.mq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javaschool.JavaSchoolBackend1.service.EventsService;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class MessageListener {

    private final EventsService eventsService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(CustomMessage message) throws IOException {
        System.out.println(message);
        eventsService.send(message);
    }

}
