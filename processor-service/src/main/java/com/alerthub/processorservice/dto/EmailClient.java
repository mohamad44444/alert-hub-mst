package com.alerthub.processorservice.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EmailClient {

    private final RestTemplate restTemplate;

    @Value("${sender.email.url:http://localhost:8088/api/email/send}")
    private String senderEmailUrl;

    public EmailClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendEmail(String recipient, String message) {
        try {
            EmailRequest request = new EmailRequest(recipient, message);
            restTemplate.postForObject(senderEmailUrl, request, String.class);

        } catch (Exception e) {
            throw new RuntimeException("Email service is not available");
        }
    }
}