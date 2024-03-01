package com.abutua.agenda.unit.domain.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import com.abutua.agenda.domain.repositories.AreaRepository;

@DataJpaTest
@EnabledIf(expression = "#{environment.acceptsProfiles('test')}", loadContext = true)
public class AreaRepositoryTest {
    
    @Autowired 
    private AreaRepository areaRepository;

    @Test
    void findActiveProfessionalsByIdShouldReturnProfessionals(){
        var expSizeArea1 = 2;
        var expSizeArea2 = 1;
        var expSizeArea3 = 1;

        var professionals = areaRepository.findActiveProfessionalsById(1);
        assertEquals(expSizeArea1, professionals.size());

        professionals = areaRepository.findActiveProfessionalsById(2);
        assertEquals(expSizeArea2, professionals.size());

        professionals = areaRepository.findActiveProfessionalsById(3);
        assertEquals(expSizeArea3, professionals.size());

    }

    @Test
    void findActiveProfessionalsByIdShouldNotReturnProfessionals(){
        var expSizeArea4 = 0;
        
        var professionals = areaRepository.findActiveProfessionalsById(4);
        assertEquals(expSizeArea4, professionals.size());
    }

}
