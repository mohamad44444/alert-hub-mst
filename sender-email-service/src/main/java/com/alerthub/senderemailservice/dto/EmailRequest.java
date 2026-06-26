package com.alerthub.senderemailservice.dto;

import lombok.Data;

@Data
public class EmailRequest {
    private String recipient;
    private String message;
}