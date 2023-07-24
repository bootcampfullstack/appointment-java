package com.abutua.agenda.domain.validation;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.domain.entities.Appointment;
import com.abutua.agenda.domain.services.exceptions.ConstrainsBusinessException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class AppointmentValidationService {


    // Inject the Validator instance here
    private final Validator validator;
    

    public AppointmentValidationService(Validator validator) {
        this.validator = validator;
    }
  

    public boolean validade(Appointment appointment){
        Set<ConstraintViolation<Appointment>> violations = validator.validate(appointment);
 
        if (!violations.isEmpty()) {
            throw new ConstrainsBusinessException(violations);
        }

        return false;
    }
    
}
