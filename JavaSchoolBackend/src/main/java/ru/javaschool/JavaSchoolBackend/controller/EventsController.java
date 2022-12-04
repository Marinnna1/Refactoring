package ru.javaschool.JavaSchoolBackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.javaschool.JavaSchoolBackend.dto.EventDto;
import ru.javaschool.JavaSchoolBackend.dto.MessageDto;
import ru.javaschool.JavaSchoolBackend.service.EventsService;
import java.util.List;



@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("events/")
@RequiredArgsConstructor
public class EventsController {

    private final EventsService eventsService;

    @PostMapping("all")
    public ResponseEntity<List<EventDto>> getData(@RequestBody EventDto eventDto) {
        List<EventDto> events = eventsService.getAll(eventDto.getPageNumber(), eventDto.getStatus(), eventDto.getFilter());
        if(!events.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(events);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(events);
    }

    @PostMapping("count")
    public ResponseEntity<Long> getEventsCount(@RequestBody EventDto eventDto) {
        Long eventsCount = eventsService.getEventsCount(eventDto);
        return ResponseEntity.status(HttpStatus.OK).body(eventsCount);
    }


    @PatchMapping("edit_status")
    public ResponseEntity<MessageDto> editStatus(@RequestBody EventDto eventDto) {
        MessageDto messageDto = eventsService.editStatus(eventDto);
        if(messageDto.getMessage().equals("Status successfully changed")) {
            return ResponseEntity.status(HttpStatus.OK).body(messageDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageDto);
    }


    @ExceptionHandler({ MethodArgumentTypeMismatchException.class})
    public ResponseEntity handleBaseExceptions() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
    }

}
