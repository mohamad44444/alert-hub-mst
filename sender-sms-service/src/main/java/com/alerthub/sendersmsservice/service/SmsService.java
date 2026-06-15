package com.alerthub.sendersmsservice.service;

import com.alerthub.sendersmsservice.dto.SmsRequest;
import com.alerthub.sendersmsservice.dto.SmsResponse;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    public SmsResponse sendSms(SmsRequest request) {

        if (request.getPhoneNumber() == null || request.getPhoneNumber().isBlank()) {
            throw new IllegalArgumentException("Phone number is required");
        }

        if (request.getMessage() == null || request.getMessage().isBlank()) {
            throw new IllegalArgumentException("Message is required");
        }

        System.out.println("=================================");
        System.out.println("SMS SENT");
        System.out.println("To: " + request.getPhoneNumber());
        System.out.println("Message: " + request.getMessage());
        System.out.println("=================================");

        return new SmsResponse(
                "SMS_SENT",
                request.getPhoneNumber(),
                request.getMessage()
        );
    }
}