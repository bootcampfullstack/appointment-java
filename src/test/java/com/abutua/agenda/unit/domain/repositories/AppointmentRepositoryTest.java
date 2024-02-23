package com.abutua.agenda.unit.domain.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.abutua.agenda.domain.entities.Client;
import com.abutua.agenda.domain.models.TimeSlot;
import com.abutua.agenda.domain.repositories.AppointmentRepository;

@DataJpaTest
public class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    void existsOpenOrPresentAppointmentsForClientShouldReturnTrue() {
        Client client3 = new Client(3L);
        LocalDate date = LocalDate.of(2024, 4, 8);
        LocalTime startTime = LocalTime.of(8, 0, 0);
        LocalTime endTime = LocalTime.of(8, 30, 0);

        var found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);

        assertTrue(found);

        startTime = LocalTime.of(8, 1);
        endTime = LocalTime.of(8, 2);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertTrue(found);

        startTime = LocalTime.of(7, 0);
        endTime = LocalTime.of(8, 2);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertTrue(found);

        startTime = LocalTime.of(7, 0);
        endTime = LocalTime.of(9, 2);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertTrue(found);

        startTime = LocalTime.of(9, 0);
        endTime = LocalTime.of(11, 0);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertTrue(found);

        startTime = LocalTime.of(6, 0);
        endTime = LocalTime.of(12, 0);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertTrue(found);

        Client client4 = new Client(4L);
        startTime = LocalTime.of(6, 0);
        endTime = LocalTime.of(12, 0);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client4, date, startTime, endTime);
        assertTrue(found);

        startTime = LocalTime.of(9, 30);
        endTime = LocalTime.of(10, 0);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client4, date, startTime, endTime);
        assertTrue(found);

        startTime = LocalTime.of(9, 15);
        endTime = LocalTime.of(10, 15);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client4, date, startTime, endTime);
        assertTrue(found);
    }

    @Test
    void existsOpenOrPresentAppointmentsForClientShouldReturnFalse() {
        Client client3 = new Client(3L);
        LocalDate date = LocalDate.of(2024, 04, 10);
        LocalTime startTime = LocalTime.of(8, 0, 0);
        LocalTime endTime = LocalTime.of(8, 30, 0);

        var found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);

        assertFalse(found);

        startTime = LocalTime.of(8, 1);
        endTime = LocalTime.of(8, 2);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertFalse(found);

        startTime = LocalTime.of(7, 0);
        endTime = LocalTime.of(8, 2);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertFalse(found);

        startTime = LocalTime.of(7, 0);
        endTime = LocalTime.of(9, 2);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertFalse(found);

        startTime = LocalTime.of(9, 0);
        endTime = LocalTime.of(11, 0);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertFalse(found);

        startTime = LocalTime.of(6, 0);
        endTime = LocalTime.of(12, 0);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertFalse(found);

        Client client4 = new Client(4L);
        startTime = LocalTime.of(6, 0);
        endTime = LocalTime.of(12, 0);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client4, date, startTime, endTime);
        assertFalse(found);

        startTime = LocalTime.of(9, 30);
        endTime = LocalTime.of(10, 0);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client4, date, startTime, endTime);
        assertFalse(found);

        startTime = LocalTime.of(9, 15);
        endTime = LocalTime.of(10, 15);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client4, date, startTime, endTime);
        assertFalse(found);

        date = LocalDate.of(2024, 04, 9);
        startTime = LocalTime.of(8, 0, 0);
        endTime = LocalTime.of(8, 30, 0);

        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client4, date, startTime, endTime);

        assertFalse(found);

        startTime = LocalTime.of(8, 1);
        endTime = LocalTime.of(8, 2);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client4, date, startTime, endTime);
        assertFalse(found);

        startTime = LocalTime.of(7, 0);
        endTime = LocalTime.of(8, 2);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client4, date, startTime, endTime);
        assertFalse(found);

        startTime = LocalTime.of(9, 0);
        endTime = LocalTime.of(9, 30);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client4, date, startTime, endTime);
        assertFalse(found);

        startTime = LocalTime.of(10, 0);
        endTime = LocalTime.of(10, 30);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client4, date, startTime, endTime);
        assertFalse(found);

    }

    @Test
    void getAvailableDaysFromProfessionalShouldReturnEmptyListDays() {
        Long professionalId = 6L;
        LocalDate start = LocalDate.of(2024, 4, 6);
        LocalDate end = LocalDate.of(2024, 4, 8);

        var foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);

        assertTrue(foundDays.isEmpty());

        start = LocalDate.of(2024, 4, 6);
        end = LocalDate.of(2024, 4, 8);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        assertTrue(foundDays.isEmpty());


        start = LocalDate.of(2024, 4, 8);
        end = LocalDate.of(2024, 4,8);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        assertTrue(foundDays.isEmpty());

    }

    @Test
    void getAvailableDaysFromProfessionalShouldReturnListDays() {
        Long professionalId = 6L;
        var start = LocalDate.of(2024, 4, 8);
        var end = LocalDate.of(2024, 4, 9);
        var foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        var expectDays = List.of(9);
        assertIterableEquals(expectDays, foundDays);

        start = LocalDate.of(2024, 4, 1);
        end = LocalDate.of(2024, 4, 15);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        expectDays = List.of(1, 2, 5, 9, 12, 15);
        assertIterableEquals(expectDays, foundDays);

        start = LocalDate.of(2024, 3, 1);
        end = LocalDate.of(2024, 3, 15);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        expectDays = List.of(1, 4, 5, 8, 11, 12, 15);
        assertIterableEquals(expectDays, foundDays);

        professionalId = 7L;
        start = LocalDate.of(2024, 4, 8);
        end = LocalDate.of(2024, 4, 8);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        expectDays = List.of(8);
        assertIterableEquals(expectDays, foundDays);

        start = LocalDate.of(2024, 4, 8);
        end = LocalDate.of(2024, 4, 9);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        expectDays = List.of(8, 9);
        assertIterableEquals(expectDays, foundDays);

        start = LocalDate.of(2024, 4, 1);
        end = LocalDate.of(2024, 4, 15);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        expectDays = List.of(1, 2, 8, 9, 15);
        assertIterableEquals(expectDays, foundDays);

        start = LocalDate.of(2024, 3, 1);
        end = LocalDate.of(2024, 3, 15);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        expectDays = List.of(4, 5, 11, 12);
        assertIterableEquals(expectDays, foundDays);
    }
    
    @Test
    void getAvailableTimesFromProfessionalShoudReturnEmptyListOfTimeSlots() {
        Long professionalId = 6L;
        LocalDate date = LocalDate.of(2024,4,3);
        List<TimeSlot> foundTimes = appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);
        assertTrue(foundTimes.isEmpty());

        
        professionalId = 6L;
        date = LocalDate.of(2024,4,4);
        foundTimes = appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);
        assertTrue(foundTimes.isEmpty());
    }

    @Test
    void getAvailableTimesFromProfessionalShoudReturnListOfTimeSlots() {

        Long professionalId = 6L;
        LocalDate date = LocalDate.of(2024,4,1);
        List<TimeSlot> foundTimes = appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);

        List<TimeSlot> expectTimes = List.of(
            createTimeSlot("08:00:00-03:00","08:30:00-03:00", true),
            createTimeSlot("08:30:00-03:00","09:00:00-03:00", true),
            createTimeSlot("09:00:00-03:00","09:30:00-03:00", true),
            createTimeSlot("09:30:00-03:00","10:00:00-03:00", true)
        );
        checkTimeSlotsLists(expectTimes, foundTimes);
        

        date = LocalDate.of(2024,4,8);
        foundTimes = appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);
        expectTimes = List.of(
            createTimeSlot("08:00:00-03:00","08:30:00-03:00", false),
            createTimeSlot("08:30:00-03:00","09:00:00-03:00", false),
            createTimeSlot("09:00:00-03:00","09:30:00-03:00", false),
            createTimeSlot("09:30:00-03:00","10:00:00-03:00", false)
        );
        checkTimeSlotsLists(expectTimes, foundTimes);


        date = LocalDate.of(2024,4,15);
        foundTimes = appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);
        expectTimes = List.of(
            createTimeSlot("08:00:00-03:00","08:30:00-03:00", true),
            createTimeSlot("08:30:00-03:00","09:00:00-03:00", true),
            createTimeSlot("09:00:00-03:00","09:30:00-03:00", true),
            createTimeSlot("09:30:00-03:00","10:00:00-03:00", false)
        );
        checkTimeSlotsLists(expectTimes, foundTimes);


        date = LocalDate.of(2024,4,5);
        foundTimes = appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);
        expectTimes = List.of(
            createTimeSlot("08:00:00-03:00","08:30:00-03:00", true),
            createTimeSlot("08:30:00-03:00","09:00:00-03:00", true),
            createTimeSlot("09:00:00-03:00","09:30:00-03:00", true),
            createTimeSlot("09:30:00-03:00","10:00:00-03:00", true),
            createTimeSlot("10:00:00-03:00","10:30:00-03:00", true),
            createTimeSlot("10:30:00-03:00","11:00:00-03:00", true),
            createTimeSlot("11:00:00-03:00","11:30:00-03:00", true),
            createTimeSlot("11:30:00-03:00","12:00:00-03:00", true)
        );
        checkTimeSlotsLists(expectTimes, foundTimes);


        date = LocalDate.of(2024,4,23);
        foundTimes = appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);
        expectTimes = List.of(
            createTimeSlot("08:00:00-03:00","08:30:00-03:00", true),
            createTimeSlot("08:30:00-03:00","09:00:00-03:00", true),
            createTimeSlot("09:00:00-03:00","09:30:00-03:00", true),
            createTimeSlot("09:30:00-03:00","10:00:00-03:00", true),
            createTimeSlot("10:00:00-03:00","10:30:00-03:00", true),
            createTimeSlot("10:30:00-03:00","11:00:00-03:00", true),
            createTimeSlot("11:00:00-03:00","11:30:00-03:00", true),
            createTimeSlot("11:30:00-03:00","12:00:00-03:00", true),
            createTimeSlot("14:00:00-03:00","14:30:00-03:00", true),
            createTimeSlot("14:30:00-03:00","15:00:00-03:00", true),
            createTimeSlot("15:00:00-03:00","15:30:00-03:00", true),
            createTimeSlot("15:30:00-03:00","16:00:00-03:00", true),
            createTimeSlot("16:00:00-03:00","16:30:00-03:00", true),
            createTimeSlot("16:30:00-03:00","17:00:00-03:00", true),
            createTimeSlot("17:00:00-03:00","17:30:00-03:00", true),
            createTimeSlot("18:30:00-03:00","18:00:00-03:00", true)
        );
        checkTimeSlotsLists(expectTimes, foundTimes);
    }


    private void checkTimeSlotsLists(List<TimeSlot> expectTimes, List<TimeSlot> foundTimes) {
        assertTrue(expectTimes.size()==foundTimes.size());

        for(int i=0;  i < expectTimes.size(); i++){
            var timeSlotExpect = expectTimes.get(i);
            var timeSlotFound = expectTimes.get(i);

            assertEquals(timeSlotExpect.getStartTime(), timeSlotFound.getStartTime());
            assertEquals(timeSlotExpect.getEndTime(), timeSlotFound.getEndTime());
            assertEquals(timeSlotExpect.isAvailable(), timeSlotFound.isAvailable());
        }
    }

    private TimeSlot createTimeSlot(String startTime, String endTime, boolean available){
        return new TimeSlot() {

            @Override
            public OffsetTime getStartTime() {
              return OffsetTime.parse(startTime);
            }

            @Override
            public OffsetTime getEndTime() {
                return OffsetTime.parse(endTime);
            }

            @Override
            public boolean isAvailable() {
                return available;
            }
        };
    }
}
