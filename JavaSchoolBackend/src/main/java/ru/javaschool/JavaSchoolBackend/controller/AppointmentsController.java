package ru.javaschool.JavaSchoolBackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.javaschool.JavaSchoolBackend.dto.AppointmentDto;
import ru.javaschool.JavaSchoolBackend.dto.MessageDto;
import ru.javaschool.JavaSchoolBackend.service.AppointmentsService;
import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("appointments/")
@RequiredArgsConstructor
public class AppointmentsController {

    private final AppointmentsService appointmentsService;

    @PostMapping("all")
    public ResponseEntity getData(@RequestBody AppointmentDto appointmentDto) {
        List<AppointmentDto> appointments = appointmentsService.getAll(appointmentDto.getPageNumber());
        if(!appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(appointments);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
    }

    @GetMapping("appointments_count")
    public ResponseEntity<Long> getAppointmentsCount() {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentsService.getAppointmentsCount());
    }

    @PostMapping("add")
    public ResponseEntity<MessageDto> addAppointment(@RequestBody AppointmentDto appointmentDto) {
        MessageDto messageDto = appointmentsService.addAppointment(appointmentDto);
        if(messageDto.getMessage().equals("Add new appointment")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(messageDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageDto);
    }


    @PatchMapping("edit_time_pattern")
    public ResponseEntity<MessageDto> editTimePattern(@RequestBody AppointmentDto appointmentDto) {
        MessageDto messageDto = appointmentsService.editTimePattern(appointmentDto);
        if(messageDto.getMessage().equals("Time pattern has been successfully changed")) {
            return ResponseEntity.status(HttpStatus.OK).body(messageDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageDto);
    }

    @PatchMapping("edit_dose")
    public ResponseEntity<MessageDto> editDose(@RequestBody AppointmentDto appointmentDto) {
        MessageDto messageDto = appointmentsService.editDose(appointmentDto);
        if(messageDto.getMessage().equals("Dose has been successfully changed")) {
            return ResponseEntity.status(HttpStatus.OK).body(messageDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageDto);
    }

    @PatchMapping("edit_period")
    public ResponseEntity<MessageDto> editPeriod(@RequestBody AppointmentDto appointmentDto) {
        MessageDto messageDto = appointmentsService.editPeriod(appointmentDto);
        if(messageDto.getMessage().equals("Period has been successfully changed")) {
            return ResponseEntity.status(HttpStatus.OK).body(messageDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageDto);
    }


    @ExceptionHandler({ MethodArgumentTypeMismatchException.class})
    public ResponseEntity handleBaseExceptions() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid method type");
    }
}