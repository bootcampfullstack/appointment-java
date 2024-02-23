package com.abutua.agenda.unit.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.abutua.agenda.domain.entities.Client;


public class ClientTest {
    

    @Test
    void getDateOfBirthShouldReturnNull(){
        Client c = new Client();
        assertNull(c.getDateOfBirth());
    }

    @Test
    void getDateOfBirthShouldReturnLocalDateObject(){
        Client c = new Client();
        LocalDate expect = LocalDate.of(2024, 10, 22);

        c.setDateOfBirth(expect);

        assertEquals(expect, c.getDateOfBirth());
    }

    @Test
    void constructorShouldSetAllAttributes(){
        String expectName = "Ana";
        String expectPhone = "15 665576369";
        LocalDate expectDateOfBirth = LocalDate.of(2022,12,22);


        Client c = new Client(expectName, expectPhone, expectDateOfBirth);


        assertEquals(expectDateOfBirth, c.getDateOfBirth());
        assertEquals(expectName, c.getName());
        assertEquals(expectPhone, c.getPhone());
        assertNull(c.getId());
    }
}
