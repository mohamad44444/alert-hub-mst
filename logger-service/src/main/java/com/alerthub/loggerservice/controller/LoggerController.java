package com.alerthub.loggerservice.controller;

import com.alerthub.loggerservice.dto.LogRequest;
import com.alerthub.loggerservice.model.LogEntry;
import com.alerthub.loggerservice.service.LoggerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LoggerController {

    private final LoggerService loggerService;

    public LoggerController(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @PostMapping
    public ResponseEntity<LogEntry> createLog(@RequestBody LogRequest request) {
        return ResponseEntity.ok(loggerService.createLog(request));
    }

    @GetMapping
    public ResponseEntity<List<LogEntry>> getAllLogs() {
        return ResponseEntity.ok(loggerService.getAllLogs());
    }

    @GetMapping("/service/{serviceName}")
    public ResponseEntity<List<LogEntry>> getLogsByServiceName(@PathVariable String serviceName) {
        return ResponseEntity.ok(loggerService.getLogsByServiceName(serviceName));
    }

    @GetMapping("/level/{logLevel}")
    public ResponseEntity<List<LogEntry>> getLogsByLogLevel(@PathVariable String logLevel) {
        return ResponseEntity.ok(loggerService.getLogsByLogLevel(logLevel));
    }
}