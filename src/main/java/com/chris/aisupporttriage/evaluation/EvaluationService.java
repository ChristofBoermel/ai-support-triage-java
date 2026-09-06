package com.chris.aisupporttriage.evaluation;

import com.chris.aisupporttriage.ticket.TicketAnalysisService;
import com.chris.aisupporttriage.ticket.TriageResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService {

    private final EvaluationCaseLoader caseLoader;
    private final TicketAnalysisService ticketAnalysisService;

    public EvaluationService(
            EvaluationCaseLoader caseLoader,
            TicketAnalysisService ticketAnalysisService
    ) {
        this.caseLoader = caseLoader;
        this.ticketAnalysisService = ticketAnalysisService;
    }

    public EvaluationReport evaluate() {
        List<EvaluationOutcome> outcomes = caseLoader.load()
                .stream()
                .map(this::evaluateCase)
                .toList();

        return new EvaluationReport(outcomes);
    }

    private EvaluationOutcome evaluateCase(EvaluationCase evaluationCase) {
        TriageResult actual = ticketAnalysisService.analyze(
                evaluationCase.request()
        );

        return new EvaluationOutcome(
                evaluationCase.name(),
                evaluationCase.expectedCategory(),
                actual.category(),
                evaluationCase.expectedSeverity(),
                actual.severity(),
                evaluationCase.expectedHumanReview(),
                actual.requiresHumanReview()
        );
    }
}
