package com.abutua.agenda.domain.entities.testrunner;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.abutua.agenda.domain.entities.Appointment;
import com.abutua.agenda.domain.entities.AppointmentType;
import com.abutua.agenda.domain.entities.Area;
import com.abutua.agenda.domain.entities.Client;
import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.domain.repositories.AppointmentRepository;
import com.abutua.agenda.domain.repositories.AppointmentTypeRepository;
import com.abutua.agenda.domain.repositories.AreaRepository;
import com.abutua.agenda.domain.repositories.ClientRepository;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;


@Component
@Profile("runner")
public class BDRunnerTestEntities implements ApplicationRunner{
    
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private AreaRepository areaRepository;
 
    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
       
        Client c1 = clientRepository.findById(1L).get();
        System.out.println(c1);

        Professional p1 = professionalRepository.findById(4L).get();
        System.out.println(p1);
        
        Area a1 = areaRepository.findById(1).get();
        System.out.println(a1);

        AppointmentType at1 = appointmentTypeRepository.findById(1).get();
        System.out.println(at1);

        Appointment appointment = new Appointment();
        appointment.setClient(c1);
        appointment.setProfessional(p1);
        appointment.setArea(a1);
        appointment.setAppointmentType(at1);
        appointment.setDate(LocalDate.now());
        appointment.setStartTime(LocalTime.parse("08:00:00"));
        appointment.setEndTime(LocalTime.parse("08:30:00"));
        appointment.setComments("Teste do runner");

        appointmentRepository.save(appointment);

    }
    
}
