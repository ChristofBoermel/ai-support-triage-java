package com.chris.aisupporttriage.evaluation;

import com.chris.aisupporttriage.ticket.Category;
import com.chris.aisupporttriage.ticket.Severity;

public record EvaluationOutcome(
        String name,
        Category expectedCategory,
        Category actualCategory,
        Severity expectedSeverity,
        Severity actualSeverity,
        boolean expectedHumanReview,
        boolean actualHumanReview
) {

    public boolean categoryMatches() {
        return expectedCategory == actualCategory;
    }

    public boolean severityMatches() {
        return expectedSeverity == actualSeverity;
    }

    public boolean humanReviewMatches() {
        return expectedHumanReview == actualHumanReview;
    }

}
