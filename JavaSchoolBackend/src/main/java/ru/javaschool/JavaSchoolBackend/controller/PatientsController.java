package ru.javaschool.JavaSchoolBackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.javaschool.JavaSchoolBackend.dto.MessageDto;
import ru.javaschool.JavaSchoolBackend.dto.PatientDto;
import ru.javaschool.JavaSchoolBackend.service.PatientsService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("patients/")
@RequiredArgsConstructor
public class PatientsController {

    private final PatientsService patientsService;


    @PostMapping("add")
    public ResponseEntity<MessageDto> addPatient(@RequestBody PatientDto patientDto) {
        MessageDto messageDto = patientsService.addPatient(patientDto);
        if(messageDto.getMessage().equals("Patient successfully added")) {
            return ResponseEntity.status(HttpStatus.OK).body(messageDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageDto);
    }


    @DeleteMapping("delete")
    public ResponseEntity<MessageDto> deletePatient(@RequestBody PatientDto patientDto) {
        MessageDto messageDto = patientsService.deletePatient(patientDto);
        if(messageDto.getMessage().equals("The patient was successfully removed")) {
            return ResponseEntity.status(HttpStatus.OK).body(messageDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageDto);
    }


    @ExceptionHandler({ MethodArgumentTypeMismatchException.class})
    public ResponseEntity handleBaseExceptions() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid method type");
    }

}