package com.chris.aisupporttriage.runbook;

import com.chris.aisupporttriage.ticket.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


@Service
public class RunbookService {

    private static final Logger logger =
            LoggerFactory.getLogger(RunbookService.class);

    private static final Map<Category, String> RUNBOOK_FILES = Map.of(
            Category.REPORTING, "reporting.md",
            Category.AUTHENTICATION, "authentication.md",
            Category.DATABASE, "database.md",
            Category.DEPLOYMENT, "deployment.md"
    );

    public List<String> actionsFor(Category category) {
        if (category == null) {
            return List.of();
        }

        String fileName = RUNBOOK_FILES.get(category);

        if (fileName == null) {
            return List.of("Review incident details with a support engineer.");
        }

        Resource resource = new ClassPathResource("runbooks/" + fileName);

        try (
            InputStream inputStream = resource.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8)
            )
        ) {
            return reader.lines()
                    .map(String::trim)
                    .filter(line -> line.startsWith("- "))
                    .map(line -> line.substring(2))
                    .toList();
        } catch (IOException exception) {
            logger.warn("Unable to read runbook: {}", fileName, exception);
            return List.of();
        }
    }
}
