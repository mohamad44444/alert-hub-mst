package com.alerthub.processorservice.dto;

public class EmailRequest {

    private String recipient;
    private String message;

    public EmailRequest() {
    }

    public EmailRequest(String recipient, String message) {
        this.recipient = recipient;
        this.message = message;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}