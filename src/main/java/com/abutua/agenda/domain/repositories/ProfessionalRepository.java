package com.abutua.agenda.domain.repositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abutua.agenda.domain.entities.Professional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
    
    boolean existsByIdAndAreas_Id(Long professionalId, Integer areaId);
 
    boolean existsAssocioationWithArea(Long professionalId, Integer areaId);
 
}
