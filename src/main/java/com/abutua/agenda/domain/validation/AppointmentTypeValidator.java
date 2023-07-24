package com.abutua.agenda.domain.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.abutua.agenda.domain.entities.AppointmentType;
import com.abutua.agenda.domain.repositories.AppointmentTypeRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AppointmentTypeValidator implements ConstraintValidator<AppointmentTypeValidation, AppointmentType> {

    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    @Override
    public boolean isValid(AppointmentType value, ConstraintValidatorContext context) {
               
        var apppointment = appointmentTypeRepository.findById(value.getId());

        if(apppointment.isEmpty()){
            return false;
        }
        else{
            return true;
        }
     
    }
}
