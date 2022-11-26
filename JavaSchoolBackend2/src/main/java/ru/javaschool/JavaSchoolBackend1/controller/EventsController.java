package ru.javaschool.JavaSchoolBackend1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.javaschool.JavaSchoolBackend1.dto.EventDto;
import ru.javaschool.JavaSchoolBackend1.service.EventsService;
import java.io.IOException;
import java.util.List;


@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping("events/")
@RequiredArgsConstructor
public class EventsController {

    private final EventsService eventsService;

    @PostMapping("all")
    public ResponseEntity getAll(@RequestBody EventDto eventDto) throws IOException {
        List<EventDto> events = eventsService.getAll(eventDto.getPageNumber());
        if(!events.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(eventsService.getAll(eventDto.getPageNumber()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found events for today");
    }


    @GetMapping("count")
    public ResponseEntity getEventsCount() throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(eventsService.getEventsCount());
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class})
    public ResponseEntity handleBaseExceptions() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
    }
}
