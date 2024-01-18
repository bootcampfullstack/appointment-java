package com.abutua.agenda.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abutua.agenda.domain.entities.Area;
import com.abutua.agenda.domain.entities.Professional;

public interface AreaRepository extends JpaRepository<Area, Integer> {
    List<Professional> findActiveProfessionalsById(Integer areaId);
}
