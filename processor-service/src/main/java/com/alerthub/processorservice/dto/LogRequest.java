package com.alerthub.processorservice.dto;

public class LogRequest {

    private String serviceName;
    private String logLevel;
    private String message;

    public LogRequest() {
    }

    public LogRequest(String serviceName, String logLevel, String message) {
        this.serviceName = serviceName;
        this.logLevel = logLevel;
        this.message = message;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}