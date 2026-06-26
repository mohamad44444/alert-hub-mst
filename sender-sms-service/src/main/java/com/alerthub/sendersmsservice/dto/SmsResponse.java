package com.alerthub.sendersmsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SmsResponse {
    private String status;
    private String phoneNumber;
    private String message;
}