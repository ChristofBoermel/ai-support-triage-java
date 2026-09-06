package com.chris.aisupporttriage.evaluation;

import com.chris.aisupporttriage.ticket.AnalyzeTicketRequest;
import com.chris.aisupporttriage.ticket.Category;
import com.chris.aisupporttriage.ticket.Severity;

public record EvaluationCase(
        String name,
        AnalyzeTicketRequest request,
        Category expectedCategory,
        Severity expectedSeverity,
        boolean expectedHumanReview
) {
}
