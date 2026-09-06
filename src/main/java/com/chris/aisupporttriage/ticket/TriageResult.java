package com.chris.aisupporttriage.ticket;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TriageResult(Category category,
                           Severity severity, String summary,
                           String affectedSystem,
                           List<String> suggestedActions,
                           @JsonProperty("requiresHumanReview")
                           boolean requiresHumanReview) {
}
