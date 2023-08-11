package com.abutua.agenda.domain.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abutua.agenda.domain.entities.Appointment;
import com.abutua.agenda.domain.mappers.AppointmentMapper;
import com.abutua.agenda.domain.mappers.AppointmentTypeMapper;
import com.abutua.agenda.domain.repositories.AppointmentTypeRepository;
import com.abutua.agenda.domain.services.usecases.write.CreateAppointmentUseCase;
import com.abutua.agenda.dto.AppointmentRequest;
import com.abutua.agenda.dto.AppointmentResponse;
import com.abutua.agenda.dto.AppointmentTypeResponse;

@Service
public class AppointmentService {

    @Autowired
    private CreateAppointmentUseCase createAppointmentUseCase;

    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest) {
        Appointment appointment = createAppointmentUseCase.executeUseCase(AppointmentMapper.fromAppointmentRequestDTO(appointmentRequest));
        return AppointmentMapper.toAppointmentResponseDTO(appointment);
    }


    @Transactional(readOnly = true)
    public List<AppointmentTypeResponse> getAllTypes(){
         return appointmentTypeRepository.findAll()
                .stream()
                .map(a -> AppointmentTypeMapper.toAppointmentResponseDTO(a))
                .collect(Collectors.toList());
    }
    
}
