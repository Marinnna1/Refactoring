package ru.javaschool.JavaSchoolBackend1.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventDto {
    private int id;
    private int pageNumber;
    private String patientName;
    private String filter;
    private String date;
    private String status;
    private String treatmentName;
    private String treatmentType;
    private String oldStatus;


}
