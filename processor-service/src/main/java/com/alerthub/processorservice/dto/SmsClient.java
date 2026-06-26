package com.alerthub.processorservice.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SmsClient {

    private final RestTemplate restTemplate;

    @Value("${sender.sms.url}")
    private String senderSmsUrl;

    public SmsClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendSms(String phoneNumber, String message) {
        try {
            SmsRequest request = new SmsRequest(phoneNumber, message);
            restTemplate.postForObject(senderSmsUrl, request, String.class);

        } catch (Exception e) {
            throw new RuntimeException("SMS service is not available");
        }
    }
}
