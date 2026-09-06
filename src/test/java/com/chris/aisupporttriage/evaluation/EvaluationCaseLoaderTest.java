package com.chris.aisupporttriage.evaluation;

import com.chris.aisupporttriage.ticket.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EvaluationCaseLoaderTest {

    private final EvaluationCaseLoader loader =
            new EvaluationCaseLoader(new ObjectMapper());

    @Test
    void loadsAllEvaluationCases() {
        List<EvaluationCase> cases = loader.load();

        assertEquals(15, cases.size());
        assertEquals(
                Category.DEPLOYMENT,
                cases.getFirst().expectedCategory()
        );
    }
}