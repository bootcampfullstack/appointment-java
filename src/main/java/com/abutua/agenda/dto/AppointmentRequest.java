package com.abutua.agenda.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.abutua.agenda.domain.validation.AppointmentTypeValidation;

/*
    {
        "date":"2023-06-26",
        "startTime": "15:30:00",
        "endTime": "16:00:00",
        "comments": "no comments",
        "type": {
            "id": 1	
        },
        "area": {
            "id": 2
        },
        "professional":  {
            "id": 5
        },
        "client": {
            "id": 2
        }    
    }
*/  

public record AppointmentRequest(
      LocalDate date,  
      LocalTime startTime,
      LocalTime endTime,
      String comments,
      IntegerDTO type,
      IntegerDTO area,
      LongDTO professional,
      LongDTO client  
) {
    
}



