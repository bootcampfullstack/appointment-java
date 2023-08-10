package com.abutua.agenda.domain.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.domain.mappers.TimeSlotMapper;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.domain.services.exceptions.ParameterException;
import com.abutua.agenda.domain.services.usecases.read.SearchProfessionalAvailabiltyDaysUseCase;
import com.abutua.agenda.domain.services.usecases.read.SearchProfessionalAvailabiltyTimesUseCase;
import com.abutua.agenda.dto.TimeSlotResponse;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfessionalService {

    @Autowired
    private SearchProfessionalAvailabiltyTimesUseCase searchProfessionalAvailabiltyTimesUseCase;

    @Autowired
    private SearchProfessionalAvailabiltyDaysUseCase searchProfessionalAvailabiltyDaysUseCase;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Transactional(readOnly = true)
    public List<TimeSlotResponse> getAvailabilityTimesFromProfessional(long professionalId, LocalDate date) {
        var professional = getProfessional(professionalId);
        var timeSlots = this.searchProfessionalAvailabiltyTimesUseCase.executeUseCase(professional.getId(), date);

        return timeSlots.stream().map(ts -> TimeSlotMapper.toTimeSlotResponseDTO(ts)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Integer> getAvailabilityDaysFromProfessional(long professionalId, int month, int year) {
        checkProfessionalExistsOrThrowsException(professionalId);
        checkMonthIsValidOrThrowsException(month);
        checkYearIsValidOrThrowsException(year);
        checkMonthAndCurrentYearAreValidOrThrowsException(month, year);

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end   = start.withDayOfMonth(start.lengthOfMonth());

        return this.searchProfessionalAvailabiltyDaysUseCase.executeUseCase(professionalId, start, end);
    }

    private void checkProfessionalExistsOrThrowsException(long professionalId) {
        if(!professionalRepository.existsById(professionalId)){
            throw new EntityNotFoundException("Profissional não encontrado.");
        }
    }

    private Professional getProfessional(long professionalId) {
        return professionalRepository.findById(professionalId)
                .orElseThrow(() -> new EntityNotFoundException("Profissional não encontrado."));
    }

    private void checkMonthAndCurrentYearAreValidOrThrowsException(int month, int year) {
        if (year == LocalDate.now().getYear() && month < LocalDate.now().getMonthValue()) {
            throw new ParameterException("Mês inválido. O mês deve ser maior ou igual ao mês corrente.");
        }
    }

    private void checkYearIsValidOrThrowsException(int year) {
        if (year < LocalDate.now().getYear()) {
            throw new ParameterException("Ano inválido. O ano deve ser maior ou igual ao ano corrente.");
        }
    }

    private void checkMonthIsValidOrThrowsException(int month) {
        if (month < 1 || month > 12) {
            throw new ParameterException("Mês inválido. O valor do mês deve ser de 1 a 12.");
        }
    }

}
