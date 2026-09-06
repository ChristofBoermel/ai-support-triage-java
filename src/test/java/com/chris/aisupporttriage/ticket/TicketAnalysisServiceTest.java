package com.chris.aisupporttriage.ticket;

import com.chris.aisupporttriage.runbook.RunbookService;
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
    @Mock
    private RunbookService runbookService;

    private TicketAnalysisService service;

    @BeforeEach
    void setUp() {
        service = new TicketAnalysisService(modelClient, runbookService);
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
                List.of("Check reporting service logs."),
                false
        );

        TriageResult modelResult = new TriageResult(
                Category.REPORTING,
                Severity.HIGH,
                "Reporting dashboard is unavailable",
                "Dashboard",
                List.of("Check application service logs"),
                false
        );

        when(modelClient.analyze(request)).thenReturn(modelResult);

        when(runbookService.actionsFor(Category.REPORTING))
                .thenReturn(List.of("Check reporting service logs."));

        TriageResult actual = service.analyze(request);

        assertEquals(expected, actual);
    }

    @Test
    void requiresHumanReviewWhenNoRunbookActionsExist() {
        AnalyzeTicketRequest request = new AnalyzeTicketRequest(
                "A customer cannot access an unknown feature",
                "customer-123",
                "Unknown feature"
        );

        TriageResult modelResult = new TriageResult(
                Category.OTHER,
                Severity.MEDIUM,
                "Customer cannot access an unknown feature",
                "Unknown feature",
                List.of("Try restarting the feature"),
                false
        );

        when(modelClient.analyze(request)).thenReturn(modelResult);
        when(runbookService.actionsFor(Category.OTHER)).thenReturn(List.of());

        TriageResult actual = service.analyze(request);

        assertEquals(Category.OTHER, actual.category());
        assertEquals(Severity.MEDIUM, actual.severity());
        assertEquals(
                List.of("Review incident details with a support engineer."),
                actual.suggestedActions()
        );
        assertTrue(actual.requiresHumanReview());
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
