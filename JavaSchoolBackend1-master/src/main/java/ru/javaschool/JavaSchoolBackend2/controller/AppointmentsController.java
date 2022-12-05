package ru.javaschool.JavaSchoolBackend2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javaschool.JavaSchoolBackend2.dto.AppointmentDto;
import ru.javaschool.JavaSchoolBackend2.dto.MessageDto;
import ru.javaschool.JavaSchoolBackend2.service.AppointmentsService;


import javax.ws.rs.Consumes;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("appointments/")
@RequiredArgsConstructor
public class AppointmentsController {

    private final AppointmentsService appointmentsService;

    @PostMapping("all")
    public List<AppointmentDto> getData(@RequestBody AppointmentDto appointmentDto) {
        return appointmentsService.getAll(appointmentDto.getPageNumber());
    }

    @PostMapping("patient")
    public ResponseEntity getData(@RequestBody String patientName) {
        List<AppointmentDto> appointmentDtos = appointmentsService.getAppointmentsForPatient(patientName);
        if(appointmentDtos.isEmpty()) {
            return ResponseEntity.status(404).body(Collections.emptyList());
        }
        return ResponseEntity.status(200).body(appointmentsService.getAppointmentsForPatient(patientName));
    }

    @GetMapping("appointments_count")
    public Long getAppointmentsCount() {
        return appointmentsService.getAppointmentsCount();
    }

    @PostMapping("add")
    public MessageDto addAppointment(@RequestBody AppointmentDto appointmentDto) {
        return appointmentsService.addAppointment(appointmentDto);
    }


    @PostMapping("delete")
    public void deleteAppointment(@RequestBody AppointmentDto appointmentDto) {
        appointmentsService.deleteAppointment(appointmentDto.getId());
    }

    @PostMapping("edit_time_pattern")
    public MessageDto editTimePattern(@RequestBody AppointmentDto appointmentDto) {
        return appointmentsService.editTimePattern(appointmentDto);
    }

    @PostMapping("edit_dose")
    public MessageDto editDose(@RequestBody AppointmentDto appointmentDto) {
        return appointmentsService.editDose(appointmentDto);
    }

    @PostMapping("edit_period")
    public MessageDto editPeriod(@RequestBody AppointmentDto appointmentDto) {
        return appointmentsService.editPeriod(appointmentDto);
    }
}