package com.abutua.agenda.domain.services.exceptions;

import java.util.Set;

import com.abutua.agenda.domain.entities.Appointment;

import jakarta.validation.ConstraintViolation;

public class ConstrainsBusinessException extends RuntimeException{
    
    private Set<ConstraintViolation<Appointment>> violations;

    public ConstrainsBusinessException(Set<ConstraintViolation<Appointment>> violations) {
        this.violations = violations;
    }

    public Set<ConstraintViolation<Appointment>> getViolations() {
        return violations;
    }

    public void setViolations(Set<ConstraintViolation<Appointment>> violations) {
        this.violations = violations;
    }

    

}