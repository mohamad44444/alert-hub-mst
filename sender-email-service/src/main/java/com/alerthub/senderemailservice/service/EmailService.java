package com.alerthub.senderemailservice.service;

import com.alerthub.senderemailservice.dto.EmailRequest;
import com.alerthub.senderemailservice.dto.EmailResponse;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public EmailResponse sendEmail(EmailRequest request) {

        if (request.getRecipient() == null || request.getRecipient().isBlank()) {
            throw new IllegalArgumentException("Recipient email is required");
        }

        if (request.getMessage() == null || request.getMessage().isBlank()) {
            throw new IllegalArgumentException("Message is required");
        }

        System.out.println("=================================");
        System.out.println("EMAIL SENT");
        System.out.println("To: " + request.getRecipient());
        System.out.println("Message: " + request.getMessage());
        System.out.println("=================================");

        return new EmailResponse(
                "EMAIL_SENT",
                request.getRecipient(),
                request.getMessage()
        );
    }
}