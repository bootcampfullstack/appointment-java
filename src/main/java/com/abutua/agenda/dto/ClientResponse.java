package com.abutua.agenda.dto;

import java.time.LocalDate;

public record ClientResponse(
        Long id,
        String name,
        String phone,
        LocalDate dateOfBirth) {
}
