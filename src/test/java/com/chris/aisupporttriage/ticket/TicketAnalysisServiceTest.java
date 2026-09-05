package com.chris.aisupporttriage.ticket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketAnalysisServiceTest {
    @Mock
    private TriageModelClient modelClient;

    @InjectMocks
    private TicketAnalysisService service;

    @BeforeEach
    void setUp() {
        service = new TicketAnalysisService(modelClient);
    }


    @Test
    void returnsModelResultWhenModelSucceeds() {
        AnalyzeTicketRequest request = new AnalyzeTicketRequest(
                "Reporting dashboard returns HTTP 500",
                "customer-123",
                "Dashboard"
        );

        TriageResult expected = new TriageResult(
                Category.REPORTING,
                Severity.HIGH,
                "Reporting dashboard is unavailable",
                "Dashboard",
                List.of("Check application error logs"),
                false
        );

        when(modelClient.analyze(request)).thenReturn(expected);

        TriageResult actual = service.analyze(request);

        assertEquals(expected, actual);
    }

    @Test
    void returnsHumanReviewFallbackWhenModelFails() {
        AnalyzeTicketRequest request = new AnalyzeTicketRequest(
                "Reporting dashboard returns HTTP 500",
                null,
                "Dashboard"
        );

        when(modelClient.analyze(any(AnalyzeTicketRequest.class)))
                .thenThrow(new IllegalStateException("Provider unavailable"));

        TriageResult actual = service.analyze(request);

        assertEquals(Category.OTHER, actual.category());
        assertEquals(Severity.MEDIUM, actual.severity());
        assertTrue(actual.requiresHumanReview());
    }
}
