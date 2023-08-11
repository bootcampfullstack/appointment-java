package com.abutua.agenda.domain.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

import com.abutua.agenda.domain.mappers.AreaMapper;
import com.abutua.agenda.domain.mappers.ProfessionalMapper;
import com.abutua.agenda.domain.repositories.AreaRepository;
import com.abutua.agenda.dto.AreaResponse;
import com.abutua.agenda.dto.ProfessionalResponse;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Transactional(readOnly = true)
    public List<AreaResponse> getAreas() {
        var areas = areaRepository.findAll();
        return areas.stream()
                .map(a -> AreaMapper.toAreaResponseDTO(a))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProfessionalResponse> getProfessionalsByArea(int areaId) {

        if (!areaRepository.existsById(areaId)) {
            throw new EntityNotFoundException("Area não cadastrada");
        } else {
            var professionalsByArea = areaRepository.findActiveProfessionalsById(areaId);

            return professionalsByArea
                    .stream()
                    .map(p -> ProfessionalMapper.toProfessionalResponseDTO(p))
                    .collect(Collectors.toList());
        }

    }
}
