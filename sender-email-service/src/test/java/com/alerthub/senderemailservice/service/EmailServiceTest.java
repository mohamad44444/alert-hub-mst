package com.alerthub.senderemailservice.service;

import com.alerthub.senderemailservice.dto.EmailRequest;
import com.alerthub.senderemailservice.dto.EmailResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailServiceTest {

    private final EmailService emailService = new EmailService();

    @Test
    void sendEmail_whenValidRequest_shouldReturnEmailSentResponse() {
        EmailRequest request = new EmailRequest();
        request.setRecipient("ameen@gmail.com");
        request.setMessage("Need to fix");

        EmailResponse response = emailService.sendEmail(request);

        assertNotNull(response);
        assertEquals("EMAIL_SENT", response.getStatus());
        assertEquals("ameen@gmail.com", response.getRecipient());
        assertEquals("Need to fix", response.getMessage());
    }

    @Test
    void sendEmail_whenRecipientIsNull_shouldThrowException() {
        EmailRequest request = new EmailRequest();
        request.setRecipient(null);
        request.setMessage("Need to fix");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> emailService.sendEmail(request)
        );

        assertEquals("Recipient email is required", exception.getMessage());
    }

    @Test
    void sendEmail_whenRecipientIsBlank_shouldThrowException() {
        EmailRequest request = new EmailRequest();
        request.setRecipient("");
        request.setMessage("Need to fix");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> emailService.sendEmail(request)
        );

        assertEquals("Recipient email is required", exception.getMessage());
    }

    @Test
    void sendEmail_whenMessageIsNull_shouldThrowException() {
        EmailRequest request = new EmailRequest();
        request.setRecipient("ameen@gmail.com");
        request.setMessage(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> emailService.sendEmail(request)
        );

        assertEquals("Message is required", exception.getMessage());
    }

    @Test
    void sendEmail_whenMessageIsBlank_shouldThrowException() {
        EmailRequest request = new EmailRequest();
        request.setRecipient("ameen@gmail.com");
        request.setMessage("");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> emailService.sendEmail(request)
        );

        assertEquals("Message is required", exception.getMessage());
    }
}