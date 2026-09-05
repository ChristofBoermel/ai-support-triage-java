package com.chris.aisupporttriage.ticket;

import java.util.List;

public record TriageResult(Category category,
                           Severity severity, String summary,
                           String affectedSystem,
                           List<String> suggestedActions,
                           boolean requiresHumanReview) {
}
