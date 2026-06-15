package com.alerthub.senderemailservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailResponse {
    private String status;
    private String recipient;
    private String message;
}