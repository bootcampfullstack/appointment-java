package com.abutua.agenda.domain.entities.testrunner;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.abutua.agenda.domain.entities.WorkScheduleItem;
import com.abutua.agenda.domain.repositories.AppointmentRepository;
import com.abutua.agenda.domain.repositories.AppointmentTypeRepository;
import com.abutua.agenda.domain.repositories.AreaRepository;
import com.abutua.agenda.domain.repositories.ClientRepository;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.domain.repositories.WorkScheduleItemRepository;


@Component
@Profile("runner")
public class BDRunnerTestEntities implements ApplicationRunner{
    
    private ClientRepository clientRepository;
    private ProfessionalRepository professionalRepository;
    private AreaRepository areaRepository;
    private AppointmentTypeRepository appointmentTypeRepository;
    private AppointmentRepository appointmentRepository;
    private WorkScheduleItemRepository workScheduleItemRepository;

    public BDRunnerTestEntities(ClientRepository clientRepository,
                                ProfessionalRepository professionalRepository,
                                AreaRepository areaRepository,
                                AppointmentTypeRepository appointmentTypeRepository,
                                AppointmentRepository appointmentRepository,
                                WorkScheduleItemRepository workScheduleItemRepository        
    ){
        this.clientRepository = clientRepository;
        this.professionalRepository = professionalRepository;
        this.areaRepository = areaRepository;
        this.appointmentTypeRepository = appointmentTypeRepository;
        this.appointmentRepository = appointmentRepository;
        this.workScheduleItemRepository = workScheduleItemRepository;
    
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
       
        // Client c1 = clientRepository.findById(1L).get();
        // System.out.println(c1);

        // Professional p1 = professionalRepository.findById(4L).get();
        // System.out.println(p1);
        
        // Area a1 = areaRepository.findById(1).get();
        // System.out.println(a1);

        // AppointmentType at1 = appointmentTypeRepository.findById(1).get();
        // System.out.println(at1);

        // Appointment appointment = new Appointment();
        // appointment.setClient(c1);
        // appointment.setProfessional(p1);
        // appointment.setArea(a1);
        // appointment.setAppointmentType(at1);
        // appointment.setDate(LocalDate.now());
        // appointment.setStartTime(LocalTime.parse("08:00:00"));
        // appointment.setEndTime(LocalTime.parse("08:30:00"));
        // appointment.setComments("Teste do runner");

        // appointmentRepository.save(appointment);


        WorkScheduleItem ws = new WorkScheduleItem(DayOfWeek.MONDAY, LocalTime.parse("08:00:00"), LocalTime.parse("12:00:00"), 8, 30);

        this.workScheduleItemRepository.save(ws);

    }
    
}
