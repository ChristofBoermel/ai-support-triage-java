package com.chris.aisupporttriage.ai;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "deepseek")
public record DeepSeekProperties(String baseUrl, String model, String apiKey, Duration timeout) {
}
