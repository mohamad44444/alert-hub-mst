package com.alerthub.loggerservice.service;

import com.alerthub.loggerservice.dto.LogRequest;
import com.alerthub.loggerservice.model.LogEntry;
import com.alerthub.loggerservice.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoggerService {

    private final LogRepository logRepository;

    public LoggerService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public LogEntry createLog(LogRequest request) {

        if (request.getServiceName() == null || request.getServiceName().isBlank()) {
            throw new IllegalArgumentException("Service name is required");
        }

        if (request.getLogLevel() == null || request.getLogLevel().isBlank()) {
            throw new IllegalArgumentException("Log level is required");
        }

        if (request.getMessage() == null || request.getMessage().isBlank()) {
            throw new IllegalArgumentException("Message is required");
        }

        LogEntry logEntry = LogEntry.builder()
                .timestamp(LocalDateTime.now())
                .serviceName(request.getServiceName())
                .logLevel(request.getLogLevel())
                .message(request.getMessage())
                .build();

        return logRepository.save(logEntry);
    }

    public List<LogEntry> getAllLogs() {
        return logRepository.findAll();
    }

    public List<LogEntry> getLogsByServiceName(String serviceName) {
        return logRepository.findByServiceName(serviceName);
    }

    public List<LogEntry> getLogsByLogLevel(String logLevel) {
        return logRepository.findByLogLevel(logLevel);
    }
}