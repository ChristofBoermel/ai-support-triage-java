package com.chris.aisupporttriage.ticket;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TicketController {

    private final TicketAnalysisService service;

    TicketController(TicketAnalysisService service) {
        this.service = service;
    }


    @PostMapping("/api/tickets/analyze")
    public TriageResult analyzeNewTicket(@Valid @RequestBody AnalyzeTicketRequest analyzeNewTicket) {
        return service.analyze(analyzeNewTicket);
    }
}
