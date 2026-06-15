package com.alerthub.loggerservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogEntry {

    @Id
    private String id;

    private LocalDateTime timestamp;

    private String serviceName;

    private String logLevel;

    private String message;
}