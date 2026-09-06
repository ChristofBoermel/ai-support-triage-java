package com.chris.aisupporttriage.evaluation;

import java.util.List;

public record EvaluationReport(List<EvaluationOutcome> outcomes) {

    public EvaluationReport {
        outcomes = List.copyOf(outcomes);
    }

    public int totalCases() {
        return outcomes.size();
    }

    public long categoryMatches() {
        return outcomes.stream()
                .filter(EvaluationOutcome::categoryMatches)
                .count();
    }

    public long severityMatches() {
        return outcomes.stream()
                .filter(EvaluationOutcome::severityMatches)
                .count();
    }

    public long humanReviewMatches() {
        return outcomes.stream()
                .filter(EvaluationOutcome::humanReviewMatches)
                .count();
    }

    public double categoryAccuracyPercent() {
        return percentage(categoryMatches());
    }

    public double severityAccuracyPercent() {
        return percentage(severityMatches());
    }

    public double humanReviewAccuracyPercent() {
        return percentage(humanReviewMatches());
    }

    private double percentage(long matches) {
        if (totalCases() == 0) {
            return 0;
        }

        return matches * 100.0 / totalCases();
    }
}
