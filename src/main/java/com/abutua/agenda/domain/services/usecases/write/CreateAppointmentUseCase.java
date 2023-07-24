package com.abutua.agenda.domain.services.usecases.write;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.abutua.agenda.domain.entities.Appointment;
import com.abutua.agenda.domain.repositories.AppointmentRepository;
import com.abutua.agenda.domain.validation.AppointmentValidationService;

@Service
public class CreateAppointmentUseCase {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentValidationService testeValidationService;


    public Appointment executeUseCase(Appointment appointment){
       
       testeValidationService.validade(appointment);

       return this.appointmentRepository.save(appointment);
    }
    
}
