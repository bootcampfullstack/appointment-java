package com.abutua.agenda.domain.services.usecases.write;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

import jakarta.persistence.EntityNotFoundException;

@Service
public class CreateAppointmentUseCase {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SearchProfessionalAvailabiltyTimesUseCase searchProfessionalAvailabiltyTimesUseCase;

    public Appointment executeUseCase(Appointment appointment) {

        checkAppointmentTypeExistsOrThrowsException(appointment.getAppointmentType());
        checkAreaExistsOrThrowsException(appointment.getArea());

        Professional professional = getProfessionalIfExistsOrThrowsException(appointment.getProfessional());
        checkProfessionalIsActiveOrThrowsException(professional);
        checkAssociationBetweenProfessionalAndAreaOrThrowsException(professional, appointment.getArea());
        checkProfessionalCanCreateAppointmentAtDateAndTimeOrThrowsException(professional, appointment);
      
        checkAppointmentIsNowOrFutureOrThrowsException(appointment.getDate(), appointment.getStartTime());

        Client client = getClientIfExistsOrThrowsException(appointment.getClient());

        return saveAppointmentAtomically(appointment, professional, client);

    }

    @Transactional
    private Appointment saveAppointmentAtomically(Appointment appointment, Professional professional, Client client) {
        checkProfessionalHasAvailableScheduleOrThorwsException(professional, appointment);
        checkClientCanCreateAppointmentAtDateAndTimeOrThrowsException(client, appointment);
        return this.appointmentRepository.save(appointment);
    }


    
    private void checkProfessionalHasAvailableScheduleOrThorwsException(Professional professional,
            Appointment appointment) {
        var timeSlots = this.searchProfessionalAvailabiltyTimesUseCase.executeUseCase(professional.getId(),
                appointment.getDate());

        if (timeSlots.isEmpty()) {
            throw new BusinessException("O professional não trabalha na data selecionada.");
        } else {
            var timeSlot = timeSlots.stream()
                    .filter(ts -> ts.getStartTime().toLocalTime().equals(appointment.getStartTime()) &&
                            ts.getEndTime().toLocalTime().equals(appointment.getEndTime()))
                    .findFirst();

            if (timeSlot.isEmpty()) {
                throw new BusinessException("O professional não trabalha no horário selecionado.");
            }
        }
    }


    private void checkProfessionalCanCreateAppointmentAtDateAndTimeOrThrowsException(Professional professional,
            Appointment appointment) {
        if (this.appointmentRepository.existsOpenOrPresentAppointmentsForProfessional(professional,
                appointment.getDate(),
                appointment.getStartTime(),
                appointment.getEndTime())) {
            throw new BusinessException(
                    "O profissional possui agendamentos em aberto para o dia e horário selecionado.");
        }
    }

    private void checkClientCanCreateAppointmentAtDateAndTimeOrThrowsException(Client client, Appointment appointment) {
        if (this.appointmentRepository.existsOpenOrPresentAppointmentsForClient(client,
                appointment.getDate(),
                appointment.getStartTime(),
                appointment.getEndTime())) {
            throw new BusinessException("O cliente possui agendamentos em aberto para o dia e horário selecionado.");
        }
    }

    private void checkAppointmentIsNowOrFutureOrThrowsException(LocalDate date, LocalTime startTime) {
        if (date.isBefore(LocalDate.now())) {
            throw new BusinessException("A data do agendamento está no passado.");
        } else {
            if (date.equals(LocalDate.now()) && startTime.isBefore(LocalTime.now())) {
                throw new BusinessException("O horário do agendamento está no passado.");
            }
        }
    }

    private void checkAssociationBetweenProfessionalAndAreaOrThrowsException(Professional professional, Area area) {
        if (!this.professionalRepository.existsAssocioationWithArea(professional.getId(), area.getId())) {
            throw new BusinessException("O profissional não atua na área selecionada.");
        }
    }

    private void checkProfessionalIsActiveOrThrowsException(Professional professional) {
        if (!professional.isActive()) {
            throw new BusinessException("Profissional desativo.");
        }
    }

    private Client getClientIfExistsOrThrowsException(Client client) {
        return this.clientRepository.findById(client.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não cadastrado."));
    }

    private Professional getProfessionalIfExistsOrThrowsException(Professional professional) {
        return this.professionalRepository.findById(professional.getId())
                .orElseThrow(() -> new EntityNotFoundException("Professional não cadastrado."));
    }

    private void checkAreaExistsOrThrowsException(Area area) {
        if (!this.areaRepository.existsById(area.getId())) {
            throw new EntityNotFoundException("Area não cadastrada.");
        }
    }

    private void checkAppointmentTypeExistsOrThrowsException(AppointmentType appointmentType) {
        if (!this.appointmentTypeRepository.existsById(appointmentType.getId())) {
            throw new EntityNotFoundException("Tipo de agendamento não cadastrado");
        }
    }

}
