package com.alerthub.loggerservice.dto;

import lombok.Data;

@Data
public class LogRequest {
    private String serviceName;
    private String logLevel;
    private String message;
}