package com.abutua.agenda.integration.web.resources;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@EnabledIf(expression = "#{environment.acceptsProfiles('test', 'dev')}", loadContext = true)
public class ProfessionalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAvailabilityTimes_Bad_Request() throws Exception {
        LocalDate dateNow  = LocalDate.now();
        LocalDate datePast = dateNow.minusWeeks(100).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        ResultActions result;
        
        result = mockMvc.perform(
            get("/professionals/6/availability-times").
            param("date", datePast.toString())
        );

        result .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getAvailabilityTimes_Ok() throws Exception {
        LocalDate dateNow = LocalDate.now();
        LocalDate dateFuture = dateNow.plusWeeks(100).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        ResultActions result;
        
        result = mockMvc.perform(
            get("/professionals/6/availability-times").
            param("date", dateFuture.toString())
        );
        
        result.andExpect(status().isOk())
              .andExpect(MockMvcResultMatchers.jsonPath("$[0].startTime").value("08:00:00"))
              .andExpect(MockMvcResultMatchers.jsonPath("$[0].endTime").value("08:30:00"))
              .andExpect(MockMvcResultMatchers.jsonPath("$[0].available").value(true))
              .andExpect(MockMvcResultMatchers.jsonPath("$[1].startTime").value("08:30:00"))
              .andExpect(MockMvcResultMatchers.jsonPath("$[1].endTime").value("09:00:00"))
              .andExpect(MockMvcResultMatchers.jsonPath("$[1].available").value(true))
              .andExpect(MockMvcResultMatchers.jsonPath("$[2].startTime").value("09:00:00"))
              .andExpect(MockMvcResultMatchers.jsonPath("$[2].endTime").value("09:30:00"))
              .andExpect(MockMvcResultMatchers.jsonPath("$[2].available").value(true))
              .andExpect(MockMvcResultMatchers.jsonPath("$[3].startTime").value("09:30:00"))
              .andExpect(MockMvcResultMatchers.jsonPath("$[3].endTime").value("10:00:00"))
              .andExpect(MockMvcResultMatchers.jsonPath("$[3].available").value(true));


         dateFuture = dateNow.plusWeeks(100).with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));     
         result = mockMvc.perform(
            get("/professionals/6/availability-times").
            param("date", dateFuture.toString())
         );

         result .andExpect(MockMvcResultMatchers.jsonPath("$[0].startTime").value("08:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endTime").value("08:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].available").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].startTime").value("08:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endTime").value("09:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].available").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].startTime").value("09:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].endTime").value("09:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].available").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].startTime").value("09:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].endTime").value("10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].available").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].startTime").value("10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].endTime").value("10:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].available").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[5].startTime").value("10:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[5].endTime").value("11:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[5].available").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[6].startTime").value("11:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[6].endTime").value("11:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[6].available").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[7].startTime").value("11:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[7].endTime").value("12:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[7].available").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[8].startTime").value("14:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[8].endTime").value("14:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[8].available").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[9].startTime").value("14:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[9].endTime").value("15:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[9].available").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[10].startTime").value("15:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[10].endTime").value("15:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[10].available").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[11].startTime").value("15:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[11].endTime").value("16:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[11].available").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[12].startTime").value("16:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[12].endTime").value("16:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[12].available").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[13].startTime").value("16:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[13].endTime").value("17:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[13].available").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[14].startTime").value("17:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[14].endTime").value("17:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[14].available").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[15].startTime").value("17:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[15].endTime").value("18:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[15].available").value(true));
        
                
         dateFuture = dateNow.plusWeeks(100).with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY));     
         result = mockMvc.perform(
            get("/professionals/6/availability-times").
            param("date", dateFuture.toString())
         );

         result .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));

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
