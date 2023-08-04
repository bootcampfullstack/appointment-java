package com.abutua.agenda.domain.services.usecases.read;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.abutua.agenda.domain.models.TimeSlot;
import com.abutua.agenda.domain.repositories.AppointmentRepository;


@Service
public class SearchProfessionalAvailabiltyTimesUseCase {

       @Autowired
       private AppointmentRepository appointmentRepository;

       public List<TimeSlot> executeUseCase(long professionalId, LocalDate date) {
              return this.appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);
       }
}
