package com.chris.aisupporttriage.ticket;

import com.chris.aisupporttriage.error.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Tickets",
        description = "Support-ticket analysis and triage"
)
@RestController
public class TicketController {

    private final TicketAnalysisService service;

    TicketController(TicketAnalysisService service) {
        this.service = service;
    }

    @Operation(
            summary = "Analyze a support ticket",
            description = "Classifies an incident and returns grounded suggested actions."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ticket analyzed successfully",
                    content = @Content(
                            schema = @Schema(implementation = TriageResult.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Request validation failed",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @PostMapping("/api/tickets/analyze")
    public TriageResult analyzeNewTicket(@Valid @RequestBody AnalyzeTicketRequest analyzeNewTicket) {
        return service.analyze(analyzeNewTicket);
    }
}
