package com.abutua.agenda.web.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abutua.agenda.domain.services.AppointmentService;
import com.abutua.agenda.dto.AppointmentTypeResponse;

@RestController
@RequestMapping("appointment-types")
public class AppointmentTypeController {

    
    private AppointmentService appointmentService;

    public AppointmentTypeController(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentTypeResponse>> getAppointmentTypes(){
        return ResponseEntity.ok().body(appointmentService.getAllTypes());
    }
    
}
