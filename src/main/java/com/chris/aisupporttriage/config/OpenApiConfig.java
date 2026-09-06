package com.chris.aisupporttriage.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI triageApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("AI Support Triage API")
                        .version("v1")
                        .description(
                                "Classifies support incidents and returns " +
                                        "grounded, auditable suggested actions."
                        ));
    }
}
