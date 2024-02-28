package com.abutua.agenda.integration.web.resources;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@EnabledIf(expression = "#{environment.acceptsProfiles('test', 'dev')}", loadContext = true)
public class AreaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAreasTest_Ok() throws Exception {

        ResultActions result = mockMvc.perform(get("/areas"));

        result  .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo("Fisioterapia")))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[1].name", equalTo("Terapia Ocupacional")))
                .andExpect(jsonPath("$[2].id", equalTo(3)))
                .andExpect(jsonPath("$[2].name", equalTo("Clínica Médica")))
                .andExpect(jsonPath("$[*].id").exists())
                .andExpect(jsonPath("$[*].name").exists());

    }

    @Test
    public void getProfessionalsByAreaTest_Ok() throws Exception {
        ResultActions result;
        
        result = mockMvc.perform(get("/areas/1/professionals"));
        result  .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", equalTo(6)))
                .andExpect(jsonPath("$[0].name", equalTo("Marcelo Silva")))
                .andExpect(jsonPath("$[0].phone", equalTo("13 999216212")))
                .andExpect(jsonPath("$[0].active", equalTo(true)))
                .andExpect(jsonPath("$[1].id", equalTo(7)))
                .andExpect(jsonPath("$[1].name", equalTo("Fernanda Cruz")))
                .andExpect(jsonPath("$[1].phone", equalTo("13 999216212")))
                .andExpect(jsonPath("$[1].active", equalTo(true)));

     
        result = mockMvc.perform(get("/areas/2/professionals"));
        result  .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo(7)))
                .andExpect(jsonPath("$[0].name", equalTo("Fernanda Cruz")))
                .andExpect(jsonPath("$[0].phone", equalTo("13 999216212")))
                .andExpect(jsonPath("$[0].active", equalTo(true)));
        
    }

    @Test
    public void getProfessionalsByAreaTest_NotFound() throws Exception {
        ResultActions result;
        result = mockMvc.perform(get("/areas/5/professionals"));
        
        result.andExpect(status().isNotFound());
    }
  

}
