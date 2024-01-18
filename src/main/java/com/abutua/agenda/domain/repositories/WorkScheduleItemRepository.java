package com.abutua.agenda.domain.repositories;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.domain.entities.WorkScheduleItem;

public interface WorkScheduleItemRepository extends JpaRepository<WorkScheduleItem, Long> {

   List<WorkScheduleItem> getWorkScheduleFromProfessionalByDayOfWeekOrderByStartTime(Professional professional, DayOfWeek dayOfWeek);
}