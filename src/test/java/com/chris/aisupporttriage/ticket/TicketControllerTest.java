package com.chris.aisupporttriage.ticket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(TicketController.class)
public class TicketControllerTest {
    @MockitoBean
    private TicketAnalysisService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        when(service.analyze(any(AnalyzeTicketRequest.class)))
                .thenReturn(new TriageResult(
                        Category.OTHER,
                        Severity.MEDIUM,
                        "Automated analysis requires human review",
                        "Dashboard",
                        List.of("Review the incident details"),
                        true
                ));
    }

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


