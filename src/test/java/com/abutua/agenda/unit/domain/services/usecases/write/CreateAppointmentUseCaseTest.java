package com.abutua.agenda.unit.domain.services.usecases.write;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abutua.agenda.domain.entities.Appointment;
import com.abutua.agenda.domain.entities.AppointmentType;
import com.abutua.agenda.domain.entities.Area;
import com.abutua.agenda.domain.entities.Client;
import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.domain.models.TimeSlot;
import com.abutua.agenda.domain.repositories.AppointmentRepository;
import com.abutua.agenda.domain.repositories.AppointmentTypeRepository;
import com.abutua.agenda.domain.repositories.AreaRepository;
import com.abutua.agenda.domain.repositories.ClientRepository;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.domain.services.exceptions.BusinessException;
import com.abutua.agenda.domain.services.usecases.read.SearchProfessionalAvailabiltyTimesUseCase;
import com.abutua.agenda.domain.services.usecases.write.CreateAppointmentUseCase;
import com.abutua.agenda.dto.ProfessionalResponse;
import com.abutua.agenda.unit.factory.TimeSlotFactory;

@ExtendWith(MockitoExtension.class)
public class CreateAppointmentUseCaseTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentTypeRepository appointmentTypeRepository;

    @Mock
    private AreaRepository areaRepository;

    @Mock
    private ProfessionalRepository professionalRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private SearchProfessionalAvailabiltyTimesUseCase searchProfessionalAvailabiltyTimesUseCase;

    @InjectMocks
    private CreateAppointmentUseCase createAppointmentUseCase;

    @Test
    public void executeUseCaseShouldPersistAppointment() {
        Appointment appointment = new Appointment();
        appointment.setDate(LocalDate.of(2024, 4, 8));
        appointment.setStartTime(LocalTime.parse("08:30:00"));
        appointment.setEndTime(LocalTime.parse("09:00:00"));

        // Mocks dos objetos necessários
        AppointmentType appointmentType = mock(AppointmentType.class);
        when(appointmentTypeRepository.existsById(any())).thenReturn(true);
        appointment.setAppointmentType(appointmentType);

        Area area = mock(Area.class);
        when(areaRepository.existsById(any())).thenReturn(true);
        appointment.setArea(area);

        Professional professional = new Professional(1L);
        professional.setActive(true);
        when(professionalRepository.findById(any())).thenReturn(java.util.Optional.of(professional));
        when(professionalRepository.existsAssocioationWithArea(any(), any())).thenReturn(true);
        appointment.setProfessional(professional);

        // Mock do comportamento da busca de disponibilidade de slots de tempo
        List<TimeSlot> someTimeSlots = createTimeSlots(true);
        when(searchProfessionalAvailabiltyTimesUseCase.executeUseCase(anyLong(), any())).thenReturn(someTimeSlots);

        Client client = new Client();
        when(clientRepository.findById(any())).thenReturn(java.util.Optional.of(client));
        appointment.setClient(client);

        when(appointmentRepository.existsOpenOrPresentAppointmentsForProfessional(any(Professional.class),
                any(LocalDate.class), any(LocalTime.class), any(LocalTime.class))).thenReturn(false);
        when(appointmentRepository.existsOpenOrPresentAppointmentsForClient(any(Client.class), any(LocalDate.class),
                any(LocalTime.class), any(LocalTime.class))).thenReturn(false);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        createAppointmentUseCase.executeUseCase(appointment);

        // Verificações
        verify(appointmentTypeRepository).existsById(any());
        verify(areaRepository).existsById(any());
        verify(professionalRepository).findById(any());
        verify(professionalRepository).existsAssocioationWithArea(any(), any());
        verify(searchProfessionalAvailabiltyTimesUseCase).executeUseCase(anyLong(), any());
        verify(clientRepository).findById(any());
        verify(appointmentRepository).save(any());
    }

    @Test
    public void executeUseCaseShouldThrowsBusinessException_WrongStartAndEndTime() {
        Appointment appointment = new Appointment();
        appointment.setDate(LocalDate.of(2024, 4, 8));
        appointment.setStartTime(LocalTime.parse("18:30:00"));
        appointment.setEndTime(LocalTime.parse("19:00:00"));

        // Mocks dos objetos necessários
        AppointmentType appointmentType = mock(AppointmentType.class);
        lenient().when(appointmentTypeRepository.existsById(any())).thenReturn(true);
        appointment.setAppointmentType(appointmentType);

        Area area = mock(Area.class);
        lenient().when(areaRepository.existsById(any())).thenReturn(true);
        appointment.setArea(area);

        Professional professional = new Professional(1L);
        professional.setActive(true);
        lenient().when(professionalRepository.findById(any())).thenReturn(java.util.Optional.of(professional));
        lenient().when(professionalRepository.existsAssocioationWithArea(any(), any())).thenReturn(true);
        appointment.setProfessional(professional);

        // Mock do comportamento da busca de disponibilidade de slots de tempo
        List<TimeSlot> someTimeSlots = createTimeSlots(true);
        when(searchProfessionalAvailabiltyTimesUseCase.executeUseCase(anyLong(), any())).thenReturn(someTimeSlots);

        Client client = new Client();
        appointment.setClient(client);
        lenient().when(clientRepository.findById(any())).thenReturn(java.util.Optional.of(client));

        // Executa o caso de uso
        assertThrows(BusinessException.class, () -> createAppointmentUseCase.executeUseCase(appointment));

        // Verificações
        verify(searchProfessionalAvailabiltyTimesUseCase).executeUseCase(anyLong(), any());
        verify(appointmentRepository, never()).save(any());

     
    }

    @Test
    public void executeUseCaseShouldThrowsBusinessException_ProfessionalBusy() {
        Appointment appointment = new Appointment();
        appointment.setDate(LocalDate.of(2024, 4, 8));
        appointment.setStartTime(LocalTime.parse("08:30:00"));
        appointment.setEndTime(LocalTime.parse("09:00:00"));

        // Mocks dos objetos necessários
        AppointmentType appointmentType = mock(AppointmentType.class);
        lenient().when(appointmentTypeRepository.existsById(any())).thenReturn(true);
        appointment.setAppointmentType(appointmentType);

        Area area = mock(Area.class);
        lenient().when(areaRepository.existsById(any())).thenReturn(true);
        appointment.setArea(area);

        Professional professional = new Professional(1L);
        professional.setActive(true);
        lenient().when(professionalRepository.findById(any())).thenReturn(java.util.Optional.of(professional));
        lenient().when(professionalRepository.existsAssocioationWithArea(any(), any())).thenReturn(true);
        appointment.setProfessional(professional);

        // Mock do comportamento da busca de disponibilidade de slots de tempo
        List<TimeSlot> someTimeSlots = createTimeSlots(false);
        lenient().when(searchProfessionalAvailabiltyTimesUseCase.executeUseCase(anyLong(), any())).thenReturn(someTimeSlots);

        Client client = new Client();
        appointment.setClient(client);
        lenient().when(clientRepository.findById(any())).thenReturn(java.util.Optional.of(client));


        when(appointmentRepository.existsOpenOrPresentAppointmentsForProfessional(any(Professional.class),
                any(LocalDate.class), any(LocalTime.class), any(LocalTime.class))).thenReturn(true);


        // Executa o caso de uso
        assertThrows(BusinessException.class, () -> createAppointmentUseCase.executeUseCase(appointment));

        // Verificações
        verify(appointmentRepository, never()).save(any());

     
    }

    private List<TimeSlot> createTimeSlots(boolean available) {
        return List.of(TimeSlotFactory.createTimeSlot("08:30:00-03:00", "09:00:00-03:00", available));
    }

}
