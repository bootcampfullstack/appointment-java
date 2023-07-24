package com.abutua.agenda.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.domain.entities.Appointment;
import com.abutua.agenda.domain.mappers.AppointmentMapper;
import com.abutua.agenda.domain.services.usecases.write.CreateAppointmentUseCase;
import com.abutua.agenda.dto.AppointmentRequest;
import com.abutua.agenda.dto.AppointmentResponse;

@Service
public class AppointmentService {

    @Autowired
    private CreateAppointmentUseCase createAppointmentUseCase;

    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest) {
        Appointment appointment = createAppointmentUseCase.executeUseCase(AppointmentMapper.fromAppointmentRequestDTO(appointmentRequest));
        return AppointmentMapper.toAppointmentResponseDTO(appointment);
    }
    
}
