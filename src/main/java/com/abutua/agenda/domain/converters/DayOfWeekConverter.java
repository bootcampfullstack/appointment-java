package com.abutua.agenda.domain.converters;

import java.time.DayOfWeek;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class DayOfWeekConverter implements AttributeConverter<DayOfWeek, Integer>{

    
   private DayOfWeekInterface converter;

    DayOfWeekConverter(DayOfWeekInterface converter){
        this.converter = converter;
    }

    
    @Override
    public Integer convertToDatabaseColumn(DayOfWeek dayOfWeek) {
       return converter.convertToDatabaseColumn(dayOfWeek);
    }

    @Override
    public DayOfWeek convertToEntityAttribute(Integer dayOfWeek) {
        return converter.convertToEntityAttribute(dayOfWeek);
    }
 
}
