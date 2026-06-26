package com.alerthub.sendersmsservice.dto;

import lombok.Data;

@Data
public class SmsRequest {
    private String phoneNumber;
    private String message;
}