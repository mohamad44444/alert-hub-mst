package com.alerthub.sendersmsservice.service;

import com.alerthub.sendersmsservice.dto.SmsRequest;
import com.alerthub.sendersmsservice.dto.SmsResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmsServiceTest {

    private final SmsService smsService = new SmsService();

    @Test
    void sendSms_whenValidRequest_shouldReturnSmsSentResponse() {
        SmsRequest request = new SmsRequest();
        request.setPhoneNumber("0509999999");
        request.setMessage("Help me by SMS");

        SmsResponse response = smsService.sendSms(request);

        assertNotNull(response);
        assertEquals("SMS_SENT", response.getStatus());
        assertEquals("0509999999", response.getPhoneNumber());
        assertEquals("Help me by SMS", response.getMessage());
    }

    @Test
    void sendSms_whenPhoneNumberIsNull_shouldThrowException() {
        SmsRequest request = new SmsRequest();
        request.setPhoneNumber(null);
        request.setMessage("Help me by SMS");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> smsService.sendSms(request)
        );

        assertEquals("Phone number is required", exception.getMessage());
    }

    @Test
    void sendSms_whenPhoneNumberIsBlank_shouldThrowException() {
        SmsRequest request = new SmsRequest();
        request.setPhoneNumber("");
        request.setMessage("Help me by SMS");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> smsService.sendSms(request)
        );

        assertEquals("Phone number is required", exception.getMessage());
    }

    @Test
    void sendSms_whenMessageIsNull_shouldThrowException() {
        SmsRequest request = new SmsRequest();
        request.setPhoneNumber("0509999999");
        request.setMessage(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> smsService.sendSms(request)
        );

        assertEquals("Message is required", exception.getMessage());
    }

    @Test
    void sendSms_whenMessageIsBlank_shouldThrowException() {
        SmsRequest request = new SmsRequest();
        request.setPhoneNumber("0509999999");
        request.setMessage("");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> smsService.sendSms(request)
        );

        assertEquals("Message is required", exception.getMessage());
    }
}