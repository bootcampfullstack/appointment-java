package com.abutua.agenda.domain.services.usecases.read;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.domain.entities.Appointment;
import com.abutua.agenda.domain.entities.AppointmentStatus;
import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.domain.entities.WorkScheduleItem;
import com.abutua.agenda.domain.models.TimeSlot;
import com.abutua.agenda.domain.repositories.AppointmentRepository;
import com.abutua.agenda.domain.repositories.WorkScheduleItemRepository;

@Service
public class SearchProfessionalAvailabiltyTimesUseCase {

       @Autowired
       private WorkScheduleItemRepository workScheduleItemRepository;

       @Autowired
       private AppointmentRepository appointmentRepository;

       public List<TimeSlot> executeUseCase(Professional professional, LocalDate date) {

              var timeSlots = new ArrayList<TimeSlot>();
              var workScheduleItems = getWorkScheduleItems(professional, date);
              var appointments      = getAppointments(professional, date);

              for(var item : workScheduleItems){
                   timeSlots.addAll(calculateTimeSlots(item, appointments, date));
              }
              
              return timeSlots;
       }

       private List<TimeSlot> calculateTimeSlots(WorkScheduleItem item, List<Appointment> appointments, LocalDate date) {
              var startTime = item.getStartTime();
              var slotSize = item.getSlotSize();
              var slots = item.getSlots();
              var timeSlots = new ArrayList<TimeSlot>();

              for(int i=0; i < slots; i++){
                     var start = startTime.plusMinutes(i * slotSize);   
                     var end   = start.plusMinutes(slotSize);

                     boolean available = isTimeSlotAvailable(start, end, appointments);
                     boolean nowOrFuture = isStartTimeValidIfDateIsToday(start, date);
                                           
                     timeSlots.add(new TimeSlot(start, end, available && nowOrFuture));                  
              }

              return timeSlots;
       }

       private boolean isStartTimeValidIfDateIsToday(LocalTime start, LocalDate date) {
              return date.isAfter(LocalDate.now()) || (date.equals(LocalDate.now()) && start.isAfter(LocalTime.now()));
       }

       /*
        * 
             Time Slot (09:00-09:30)
             None Match: Appointments: (08:00-08:30), (08:30-09:00), (09:30 - 10:00) 
             Match: Apppointments: (09:10-09:20), (08:00-10:00), (09:10-10:00), (08:00-09:10)

        */
       private boolean isTimeSlotAvailable(LocalTime start, LocalTime end, List<Appointment> appointments) {
              return appointments.stream().noneMatch( a -> a.getStartTime().isBefore(end) && 
                                                           a.getEndTime().isAfter(start)  &&
                                                           (  
                                                               a.getStatus().equals(AppointmentStatus.OPEN)     ||
                                                               a.getStatus().equals(AppointmentStatus.PRESENT) 
                                                           )
                                                    );
       }

       private List<Appointment> getAppointments(Professional professional, LocalDate date) {
              return this.appointmentRepository.findByProfessionalIdAndDate(professional.getId(), date);
       }

       private List<WorkScheduleItem> getWorkScheduleItems(Professional professional, LocalDate date) {
              return this.workScheduleItemRepository.getWorkScheduleFromProfessionalByDayOfWeekOrderByStartTime(professional, date.getDayOfWeek());
       }
}
