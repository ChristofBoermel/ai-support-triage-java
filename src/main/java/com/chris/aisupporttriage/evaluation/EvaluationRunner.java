package com.chris.aisupporttriage.evaluation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("evaluation")
public class EvaluationRunner implements ApplicationRunner {

    private static final Logger logger =
            LoggerFactory.getLogger(EvaluationRunner.class);

    private final EvaluationService evaluationService;

    public EvaluationRunner(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @Override
    public void run(ApplicationArguments args) {
        EvaluationReport report = evaluationService.evaluate();

        logger.info(
                "Evaluation complete: total={}, category={}/{}, severity={}/{}, humanReview={}/{}",
                report.totalCases(),
                report.categoryMatches(),
                report.totalCases(),
                report.severityMatches(),
                report.totalCases(),
                report.humanReviewMatches(),
                report.totalCases()
        );

        logger.info(
                "Accuracy: category={}%, severity={}%, humanReview={}%",
                report.categoryAccuracyPercent(),
                report.severityAccuracyPercent(),
                report.humanReviewAccuracyPercent()
        );
    }
}
