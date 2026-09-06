package com.chris.aisupporttriage.ticket;

import com.chris.aisupporttriage.runbook.RunbookService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class TicketAnalysisService {

    private final TriageModelClient modelClient;
    private final RunbookService runbookService;
    private static final Logger logger =
            LoggerFactory.getLogger(TicketAnalysisService.class);

    public TicketAnalysisService(TriageModelClient modelClient, RunbookService runbookService) {
        this.modelClient = modelClient;
        this.runbookService = runbookService;
    }

    public TriageResult analyze(AnalyzeTicketRequest request){
        try {
            TriageResult modelResult = modelClient.analyze(request);
            List<String> groundedActions = runbookService.actionsFor(modelResult.category());

            if (groundedActions.isEmpty()) {
                return new TriageResult(
                        modelResult.category(),
                        modelResult.severity(),
                        modelResult.summary(),
                        modelResult.affectedSystem(),
                        List.of("Review incident details with a support engineer."),
                        true
                );
            }
                return new TriageResult(
                    modelResult.category(),
                    modelResult.severity(),
                    modelResult.summary(),
                    modelResult.affectedSystem(),
                    groundedActions,
                    modelResult.requiresHumanReview()
            );

        } catch (RuntimeException exception) {
            logger.warn(
                    "DeepSeek triage failed; returning human-review fallback",
                    exception
            );
            return fallbackResult(request);
        }
    }

    private TriageResult fallbackResult(AnalyzeTicketRequest request) {
        return new TriageResult(
                Category.OTHER,
                Severity.MEDIUM,
                "Automated analysis requires human review",
                request.affectedService(),
                List.of("Review the incident details"),
                true
        );
    }
}
