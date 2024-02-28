package com.abutua.agenda.integration.web.resources;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.abutua.agenda.dto.ClientRequest;
import com.fasterxml.jackson.databind.ObjectMapper;



import java.time.LocalDate;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@EnabledIf(expression = "#{environment.acceptsProfiles('test', 'dev')}", loadContext = true)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void saveClient_Created() throws Exception {
        ClientRequest clientRequest = new ClientRequest("Fernanda Gomes", "11 92213212", LocalDate.of(1978, 10, 21));

        mockMvc.perform(MockMvcRequestBuilders.post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Fernanda Gomes"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("11 92213212"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value("1978-10-21"));
    }

    @Test
    public void saveClient_Unprocessable_Entity() throws Exception {
        ClientRequest clientRequest = new ClientRequest("", "", null);
        mockMvc.perform(MockMvcRequestBuilders.post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", hasSize(3)));
    }

    
    @Test
    public void saveClient_Created_And_Get() throws Exception {
        ClientRequest clientRequest = new ClientRequest("Fernanda Gomes", "11 92213212", LocalDate.of(1978, 10, 21));

        
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/clients"));

        result  .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content", hasSize(3)))
                        .andExpect(jsonPath("$.content[*].id", containsInAnyOrder(3, 4, 5)))
                        .andExpect(jsonPath("$.content[*].name", containsInAnyOrder("Ana Maria", "Pedro Silva", "Marco Nunes")))
                        .andExpect(jsonPath("$.content[*].phone", containsInAnyOrder("15 992231122", "15 923233212", "11 902324322")))
                        .andExpect(jsonPath("$.content[*].dateOfBirth", containsInAnyOrder("2000-08-02", "1998-01-22", "1998-01-22")));


        result = mockMvc.perform(MockMvcRequestBuilders.post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientRequest)));

        result  .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Fernanda Gomes"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("11 92213212"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value("1978-10-21"));

        
        
        result = mockMvc.perform(MockMvcRequestBuilders.get("/clients"));

        result  .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(4)))
                .andExpect(jsonPath("$.content[*].id", containsInAnyOrder(3, 4, 5, 9)))
                .andExpect(jsonPath("$.content[*].name", containsInAnyOrder("Ana Maria", "Pedro Silva", "Marco Nunes", "Fernanda Gomes")))
                .andExpect(jsonPath("$.content[*].phone", containsInAnyOrder("15 992231122", "15 923233212", "11 902324322", "11 92213212")))
                .andExpect(jsonPath("$.content[*].dateOfBirth", containsInAnyOrder("2000-08-02", "1998-01-22", "1998-01-22", "1978-10-21")));
               
    }
}
