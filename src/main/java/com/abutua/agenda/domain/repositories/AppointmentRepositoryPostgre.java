package com.abutua.agenda.domain.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abutua.agenda.domain.models.TimeSlot;

@Repository
@Profile({"dev","prod"})
public interface AppointmentRepositoryPostgre extends AppointmentRepository{

    /* 
           WITH RECURSIVE SequencialCTE(cont) AS (
                SELECT 0
                UNION ALL
                SELECT cont + 1 FROM SequencialCTE WHERE cont < 31
                ),
                WorkSchedule (_start_time, _start_time_plus_inc, _slot_size, _end_time, _day_of_week) AS (
                SELECT 
                        start_time, 
                        start_time + MAKE_INTERVAL( mins => slot_size),
                        slot_size,
                        end_time,
                        day_of_week
                FROM 
                        TBL_WORK_SCHEDULE_ITEM 
                WHERE 
                        professional_id = 6

                UNION ALL

                SELECT 
                        _start_time + MAKE_INTERVAL( mins => _slot_size), 
                        _start_time_plus_inc + MAKE_INTERVAL( mins => _slot_size), 
                        _slot_size,
                        _end_time,
                        _day_of_week
                FROM
                        WorkSchedule
                WHERE 
                        _start_time < _end_time - MAKE_INTERVAL( mins => _slot_size)
                )
                SELECT 
                DISTINCT EXTRACT(DAY FROM work_day_available) AS day_of_month
                FROM 
                WorkSchedule
                JOIN (
                SELECT 
                         DATE('2024-02-01 -03') + cont AS work_day_available
                FROM (
                        SELECT 
                        cont
                        FROM 
                        SequencialCTE
                )
                WHERE 
                        cont <=  DATE('2024-02-29 -03') -  DATE('2024-02-01 -03')
                ) 
                ON EXTRACT(DOW FROM work_day_available) = _day_of_week

                LEFT JOIN 
                TBL_APPOINTMENT a
                ON 
                a.professional_id = 6 AND
                a.start_time < _start_time_plus_inc AND
                a.end_time > _start_time AND
                a.date = work_day_available AND
                (a.status = 'OPEN' OR a.status = 'PRESENT')
                WHERE a.ID IS NULL;
    */
    @Query(value=
    "   WITH RECURSIVE SequencialCTE(cont) AS ( "+ 
    "   SELECT 0 "+ 
    "   UNION ALL "+ 
    "   SELECT cont + 1 FROM SequencialCTE WHERE cont < 31 "+ 
    "   ), "+ 
    "   WorkSchedule (_start_time, _start_time_plus_inc, _slot_size, _end_time, _day_of_week) AS ( "+ 
    "   SELECT  "+ 
    "           start_time,                                         " + 
    "           start_time   +  MAKE_INTERVAL( mins => slot_size),  " + 
    "           slot_size,                                          " + 
    "           end_time,                                           " + 
    "           day_of_week                                         " + 
    "   FROM  "+ 
    "           TBL_WORK_SCHEDULE_ITEM  "+ 
    "   WHERE  "+ 
    "           professional_id = :professionalId "+ 

    "   UNION ALL "+ 

    "   SELECT  "+ 
    "           _start_time          +  MAKE_INTERVAL( mins => _slot_size),  "+ 
    "           _start_time_plus_inc +  MAKE_INTERVAL( mins => _slot_size),  "+ 
    "           _slot_size, "+ 
    "           _end_time, "+ 
    "           _day_of_week "+ 
    "   FROM "+ 
    "           WorkSchedule "+ 
    "   WHERE  "+ 
    "           _start_time < _end_time - MAKE_INTERVAL( mins => _slot_size) "+ 
    "   ) "+ 
    "   SELECT  "+ 
    "   DISTINCT EXTRACT(DAY FROM work_day_available) AS day_of_month "+ 
    "   FROM  "+ 
    "   WorkSchedule "+ 
    "   JOIN ( "+ 
    "   SELECT  "+ 
    "            DATE(:start) + cont AS work_day_available "+ 
    "   FROM ( "+ 
    "           SELECT  "+ 
    "           cont "+ 
    "           FROM  "+ 
    "           SequencialCTE "+ 
    "   ) "+ 
    "   WHERE  "+ 
    "           cont <=  DATE(:end) -  DATE(:start) "+ 
    "   )  "+ 
    "   ON EXTRACT(DOW FROM work_day_available) = _day_of_week "+ 

    "   LEFT JOIN  "+ 
    "   TBL_APPOINTMENT a "+ 
    "   ON  "+ 
    "   a.professional_id = :professionalId AND "+ 
    "   a.start_time < _start_time_plus_inc AND "+ 
    "   a.end_time > _start_time AND "+ 
    "   a.date = work_day_available AND "+ 
    "   (a.status = 'OPEN' OR a.status = 'PRESENT') "+ 
    "   WHERE a.ID IS NULL; "
    , nativeQuery = true)
    public List<Integer> getAvailableDaysFromProfessional(long professionalId, LocalDate start, LocalDate end);


    /*
                WITH RECURSIVE 
                WorkSchedule (_start_time, _start_time_plus_inc, _slot_size, _end_time, _day_of_week) AS (
                SELECT
                        start_time,
                        start_time + INTERVAL '1 minute' * slot_size AS _start_time_plus_inc,
                        slot_size,
                        end_time,
                        day_of_week
                FROM
                        TBL_WORK_SCHEDULE_ITEM
                WHERE
                        professional_id = 6
                UNION ALL

                SELECT
                        _start_time + INTERVAL '1 minute' * _slot_size,
                        _start_time_plus_inc + INTERVAL '1 minute' * _slot_size,
                        _slot_size,
                        _end_time,
                        _day_of_week
                FROM
                        WorkSchedule
                WHERE
                        _start_time < _end_time - INTERVAL '1 minute' * _slot_size
                )

                SELECT 
                _start_time AS startTime,
                _start_time_plus_inc AS endTime,
                CASE 
                        WHEN a.date IS NULL THEN TRUE
                        ELSE FALSE
                END AS available
                FROM 
                WorkSchedule 
                LEFT JOIN 
                TBL_APPOINTMENT a 
                ON
                a.professional_id = 6                                           AND
                a.date = '2024-04-16'                                          AND
                a.start_time < _start_time_plus_inc                   AND 
                a.end_time >  _start_time                                  AND 
                (a.status = 'OPEN' OR a.status = 'PRESENT' )
                ORDER BY startTime;

      
   */

    @Query(value=
        "  WITH RECURSIVE  " +
        "  WorkSchedule (_start_time, _start_time_plus_inc, _slot_size, _end_time, _day_of_week) AS ( " +
        "  SELECT " +
        "          start_time, " +
        "          start_time + INTERVAL '1 minute' * slot_size AS _start_time_plus_inc, " +
        "          slot_size, " +
        "          end_time, " +
        "          day_of_week " +
        "  FROM " +
        "          TBL_WORK_SCHEDULE_ITEM " +
        "  WHERE " +
        "          professional_id = :professionalId " +
        "  UNION ALL " +
        "  SELECT " +
        "          _start_time + INTERVAL '1 minute' * _slot_size, " +
        "          _start_time_plus_inc + INTERVAL '1 minute' * _slot_size, " +
        "          _slot_size, " +
        "          _end_time, " +
        "          _day_of_week " +
        "  FROM " +
        "          WorkSchedule " +
        "  WHERE " +
        "          _start_time < _end_time - INTERVAL '1 minute' * _slot_size " +
        "  ) " +
        "  SELECT  " +
        "  _start_time AS startTime, " +
        "  _start_time_plus_inc AS endTime, " +
        "  CASE  " +
        "          WHEN a.date IS NULL THEN TRUE " +
        "          ELSE FALSE " +
        "  END AS available " +
        "  FROM  " +
        "  WorkSchedule  " +
        "  LEFT JOIN  " +
        "  TBL_APPOINTMENT a  " +
        "  ON " +
        "  a.professional_id = :professionalId                   AND " +
        "  a.date = DATE(:date)                                        AND " +
        "  a.start_time < _start_time_plus_inc                   AND  " +
        "  a.date = work_day_available                             AND " +
        "  a.end_time >  _start_time                             AND  " +
        "  (a.status = 'OPEN' OR a.status = 'PRESENT' ) " +
        "  ORDER BY startTime "
    , nativeQuery = true)
    public List<TimeSlot> getAvailableTimesFromProfessional(long professionalId, LocalDate date);


}
