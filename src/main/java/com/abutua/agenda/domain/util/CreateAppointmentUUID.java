package com.abutua.agenda.domain.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.abutua.agenda.domain.entities.Appointment;
import com.abutua.agenda.domain.entities.AppointmentStatus;

public class CreateAppointmentUUID {

    public static UUID generateProfessionalUUID(Appointment appointment){
        String combinedValue = appointment.getProfessional().getId() + 
                               appointment.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + 
                               appointment.getStartTime().format(DateTimeFormatter.ISO_LOCAL_TIME);

        if(appointment.getStatus() == AppointmentStatus.CANCEL || appointment.getStatus() == AppointmentStatus.ABSENT){
            combinedValue += System.currentTimeMillis();
        }
                               
        return UUID.nameUUIDFromBytes(combinedValue.getBytes());
    }

     public static UUID generateClientUUID(Appointment appointment){
        String combinedValue = appointment.getClient().getId() + 
                               appointment.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + 
                               appointment.getStartTime().format(DateTimeFormatter.ISO_LOCAL_TIME);
        
        if(appointment.getStatus() == AppointmentStatus.CANCEL || appointment.getStatus() == AppointmentStatus.ABSENT){
            combinedValue += System.currentTimeMillis();
        }

        return UUID.nameUUIDFromBytes(combinedValue.getBytes());
    }
    
}
