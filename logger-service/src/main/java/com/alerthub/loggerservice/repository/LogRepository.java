package com.alerthub.loggerservice.repository;

import com.alerthub.loggerservice.model.LogEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogRepository extends MongoRepository<LogEntry, String> {

    List<LogEntry> findByServiceName(String serviceName);

    List<LogEntry> findByLogLevel(String logLevel);
}