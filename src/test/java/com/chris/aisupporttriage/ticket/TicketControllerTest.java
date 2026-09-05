package com.chris.aisupporttriage.ticket;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testTicketController() throws Exception{
        String url = "/api/tickets/analyze";

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content("""
                        {
                            "description": "Dashboard access failed after deployment"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("OTHER"))
                .andExpect(jsonPath("$.severity").value("MEDIUM"))
                .andExpect(jsonPath("$.requiresHumanReview").value(true));

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content("""
                        {
                            "description" : " "
                        }
                        """))
                .andExpect(status().isBadRequest());
    }
}


