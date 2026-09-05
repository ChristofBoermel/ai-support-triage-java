package com.chris.aisupporttriage.ticket;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class TicketAnalysisService {

    private final TriageModelClient modelClient;
    private static final Logger logger =
            LoggerFactory.getLogger(TicketAnalysisService.class);

    public TicketAnalysisService(TriageModelClient modelClient) {
        this.modelClient = modelClient;
    }

    public TriageResult analyze(AnalyzeTicketRequest request){
        try {
            return modelClient.analyze(request);
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
