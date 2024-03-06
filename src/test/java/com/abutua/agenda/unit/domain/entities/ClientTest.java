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
    void getDateOfBirthShouldReturnLocalDate(){
        Client c = new Client();
        LocalDate expDate = LocalDate.parse("2024-08-01");

        c.setDateOfBirth(expDate);

        assertEquals(expDate,c.getDateOfBirth());
    }

    @Test
    void constructoShouldSetAllAttributes(){

        LocalDate expDate = LocalDate.parse("2024-08-01");
        String expName = "Ana";
        String expComments = "none";
        String expPhone = "15 9999999999";


        Client c = new Client(expName,expPhone,expDate,expComments);
        
        assertEquals(expName, c.getName());
        assertEquals(expPhone, c.getPhone());
        assertEquals(expDate, c.getDateOfBirth());
        assertEquals(expComments, c.getComments());
        assertNull(c.getId());

    }

}
