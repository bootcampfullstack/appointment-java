package com.abutua.agenda.domain.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.domain.mappers.TimeSlotMapper;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.domain.services.usecases.read.SearchProfessionalAvailabiltyTimesUseCase;
import com.abutua.agenda.dto.TimeSlotResponse;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfessionalService {

    @Autowired
    private SearchProfessionalAvailabiltyTimesUseCase searchProfessionalAvailabiltyTimesUseCase;

    @Autowired
    private ProfessionalRepository professionalRepository;

    public List<TimeSlotResponse> getAvailabilityTimes(long professionaId, LocalDate date){

        var professional = professionalRepository.findById(professionaId)
                                                 .orElseThrow(() -> new EntityNotFoundException("Profissional  não encontrado."));
                                                 
        var timeSlots =  this.searchProfessionalAvailabiltyTimesUseCase.executeUseCase(professional, date);
       
        return timeSlots.stream().map(ts -> TimeSlotMapper.toTimeSlotResponseDTO(ts)).collect(Collectors.toList());      
    }
    
}
