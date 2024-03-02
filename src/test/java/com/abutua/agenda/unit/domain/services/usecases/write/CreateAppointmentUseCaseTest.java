package com.abutua.agenda.unit.domain.services.usecases.write;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.abutua.agenda.domain.entities.AppointmentType;
import com.abutua.agenda.domain.entities.Area;
import com.abutua.agenda.domain.entities.Client;
import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.domain.repositories.AppointmentRepository;
import com.abutua.agenda.domain.repositories.AppointmentTypeRepository;
import com.abutua.agenda.domain.repositories.AreaRepository;
import com.abutua.agenda.domain.repositories.ClientRepository;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.domain.services.exceptions.BusinessException;
import com.abutua.agenda.domain.services.usecases.read.SearchProfessionalAvailabiltyTimesUseCase;
import com.abutua.agenda.domain.services.usecases.write.CreateAppointmentUseCase;
import static org.mockito.BDDMockito.*;
import static java.util.Optional.*;

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
    void executeUseCaseShouldPersistAppointment(){

         // Mocks dos objetos necessários
        AppointmentType appointmentType = mock(AppointmentType.class);
        given(appointmentTypeRepository.existsById(anyInt())).willReturn(true);
    
        Area area = mock(Area.class);
        given(areaRepository.existsById(anyInt())).willReturn(true);
    
        Professional professional = mock(Professional.class);
        given(professional.isActive()).willReturn(true);
        given(professionalRepository.findById(anyLong())).willReturn(of(professional));
        given(professionalRepository.existsAssocioationWithArea(anyLong(), anyInt())).willReturn(true);
   
        Client client = mock(Client.class);
        given(clientRepository.findById(anyLong())).willReturn(of(client));

        // if(this.appointmentRepository.existsOpenOrPresentAppointmentsForProfessional( professional, 
        //                                                                         appointment.getDate(), 
        //                                                                         appointment.getStartTime(),
        //                                                                         appointment.getEndTime())){
        //       throw new BusinessException("O profissional possui agendamentos em aberto para o dia e horário selecionado.");
        // }

        // private void checkClientCanCreateAppointmentAtDateAndTimeOrThrowsException(Client client, Appointment appointment) {
        //     if(this.appointmentRepository.existsOpenOrPresentAppointmentsForClient( client, 
        //                                                                             appointment.getDate(), 
        //                                                                             appointment.getStartTime(),
        //                                                                             appointment.getEndTime())){
        //           throw new BusinessException("O cliente possui agendamentos em aberto para o dia e horário selecionado.");
        //     }
        // }    

        // var timeSlots = this.searchProfessionalAvailabiltyTimesUseCase.executeUseCase

        //  return this.appointmentRepository.save(appointment);

        assertTrue(false);
    }

    @Test
    public void executeUseCaseShouldThrowsBusinessException_WrongStartAndEndTime() {
        assertTrue(false);
    }

    @Test
    public void executeUseCaseShouldThrowsBusinessException_ProfessionalNotAvailable() {
        assertTrue(false);
    }

}
 