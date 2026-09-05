package com.chris.aisupporttriage.ticket;

public interface TriageModelClient {
    TriageResult analyze(AnalyzeTicketRequest request);
}
