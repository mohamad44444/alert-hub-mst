package com.alerthub.processorservice.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LoggerClient {

    private final RestTemplate restTemplate;

    @Value("${logger.service.url}")
    private String loggerServiceUrl;

    public LoggerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendLog(String logLevel, String message) {
        try {
            LogRequest request = new LogRequest(
                    "processor-service",
                    logLevel,
                    message
            );

            restTemplate.postForObject(loggerServiceUrl, request, String.class);

        } catch (Exception e) {
            System.out.println("Failed to send log to logger-service: " + e.getMessage());
        }
    }
}