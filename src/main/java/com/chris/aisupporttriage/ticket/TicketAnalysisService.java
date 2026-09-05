package com.chris.aisupporttriage.ticket;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketAnalysisService {
    public TriageResult analyze(AnalyzeTicketRequest request){
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
