package com.abutua.agenda.unit.domain.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import com.abutua.agenda.domain.repositories.AreaRepository;

@DataJpaTest
@EnabledIf(expression = "#{environment.acceptsProfiles('test', 'dev')}", loadContext = true)
public class AreaRepositoryTest {

    @Autowired
    private AreaRepository areaRepository;

    @Test
    void findActiveProfessionalsByIdShouldReturnActiveProfessionals() {

        var expectSizeArea1 = 2;
        var expectSizeArea2 = 1;
        var expectSizeArea3 = 1;

        var professionals = areaRepository.findActiveProfessionalsById(1);
        assertEquals(expectSizeArea1, professionals.size());

        professionals = areaRepository.findActiveProfessionalsById(2);
        assertEquals(expectSizeArea2, professionals.size());

        professionals = areaRepository.findActiveProfessionalsById(3);
        assertEquals(expectSizeArea3, professionals.size());
    }

    @Test
    void findActiveProfessionalsByIdShouldReturnActiveProfessionalsNamesFromArea() {

        List<String> expectNames = List.of("Fernanda Cruz","Marcelo Silva");

        var professionals = areaRepository.findActiveProfessionalsById(1);

        var foundNames = professionals.stream().map(p -> p.getName()).toList();

        Set<String> expectNamesSet = new HashSet<>(expectNames);
        Set<String> foundNamesSet = new HashSet<>(foundNames);

        assertIterableEquals(expectNamesSet, foundNamesSet);
    }
}
