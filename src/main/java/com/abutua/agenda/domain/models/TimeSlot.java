package com.abutua.agenda.domain.models;

import java.time.OffsetTime;

public interface TimeSlot {
    OffsetTime getStartTime();
    OffsetTime getEndTime();
    boolean isAvailable();
}
