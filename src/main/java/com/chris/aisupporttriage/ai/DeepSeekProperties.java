package com.chris.aisupporttriage.ai;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "deepseek")
public record DeepSeekProperties(String baseUrl, String model, String apiKey) {
}
