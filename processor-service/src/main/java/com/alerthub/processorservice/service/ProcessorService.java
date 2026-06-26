package com.alerthub.processorservice.service;

import com.alerthub.processorservice.dto.ActionDto;
import com.alerthub.processorservice.dto.MetricDto;
import com.alerthub.processorservice.dto.PlatformInformationDto;
import com.alerthub.processorservice.dto.ProcessResultDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alerthub.processorservice.dto.EmailClient;
import com.alerthub.processorservice.dto.SmsClient;
import com.alerthub.processorservice.dto.LoggerClient;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class ProcessorService {

    private final RestTemplate restTemplate;

    private final EmailClient emailClient;

    private final SmsClient smsClient;

    private final LoggerClient loggerClient;

    @Value("${services.action.url}")
    private String actionServiceUrl;

    @Value("${services.metric.url}")
    private String metricServiceUrl;

    @Value("${services.loader.url}")
    private String loaderServiceUrl;

    public ProcessorService(RestTemplate restTemplate,EmailClient emailClient,SmsClient smsClient,LoggerClient loggerClient) {
        this.restTemplate = restTemplate;
        this.emailClient = emailClient;
        this.smsClient = smsClient;
        this.loggerClient = loggerClient;
    }

    public ProcessResultDto processAction(Long actionId) {
        ActionDto action = restTemplate.getForObject(
                actionServiceUrl + "/api/actions/" + actionId,
                ActionDto.class
        );

        if (action == null) {
            throw new RuntimeException("Action not found");
        }

        if (Boolean.TRUE.equals(action.getIsDeleted())) {
            return new ProcessResultDto(
                    actionId,
                    false,
                    action.getActionType(),
                    action.getTo(),
                    "Action is deleted"
            );
        }

        if (!Boolean.TRUE.equals(action.getIsEnabled())) {
            return new ProcessResultDto(
                    actionId,
                    false,
                    action.getActionType(),
                    action.getTo(),
                    "Action is disabled"
            );
        }

        boolean satisfied = evaluateCondition(action.getConditionJson());

        if (satisfied && "EMAIL".equalsIgnoreCase(action.getActionType())) {
            emailClient.sendEmail(action.getTo(), action.getMessage());
            loggerClient.sendLog("INFO", "Message sent to Email service");
        }

        if (satisfied && "SMS".equalsIgnoreCase(action.getActionType())) {
            smsClient.sendSms(action.getTo(), action.getMessage());
            loggerClient.sendLog("INFO", "Message sent to SMS service");
        }

        return new ProcessResultDto(
                action.getId(),
                satisfied,
                action.getActionType(),
                action.getTo(),
                satisfied ? action.getMessage() : "Condition not satisfied"
        );
    }

    private boolean evaluateCondition(String conditionJson) {

        String cleaned = conditionJson
                .replace(" ", "")
                .replace("[[", "")
                .replace("]]", "");

        String[] groups = cleaned.split("\\],\\[");

        for (String group : groups) {
            String[] metricIds = group.split(",");

            boolean allMetricsSatisfied = true;

            for (String metricIdText : metricIds) {
                Long metricId = Long.parseLong(metricIdText);

                boolean metricSatisfied = evaluateMetric(metricId);

                if (!metricSatisfied) {
                    allMetricsSatisfied = false;
                    break;
                }
            }

            if (allMetricsSatisfied) {
                return true;
            }
        }

        return false;
    }

    private boolean evaluateMetric(Long metricId) {
        MetricDto metric = restTemplate.getForObject(
                metricServiceUrl + "/api/metrics/" + metricId,
                MetricDto.class
        );

        if (metric == null) {
            return false;
        }

        PlatformInformationDto[] dataArray = restTemplate.getForObject(
                loaderServiceUrl + "/api/platform-information/label/" + metric.getLabel(),
                PlatformInformationDto[].class
        );

        if (dataArray == null) {
            return false;
        }

        List<PlatformInformationDto> data = Arrays.asList(dataArray);

        long count = data.stream()
                .filter(item -> isWithinTimeFrame(item.getTimestamp(), metric.getTimeFrameHours()))
                .count();

        return count >= metric.getThreshold();
    }

    private boolean isWithinTimeFrame(String timestampText, Integer timeFrameHours) {
        try {
            OffsetDateTime timestamp = OffsetDateTime.parse(timestampText);
            OffsetDateTime fromTime = OffsetDateTime.now().minusHours(timeFrameHours);
            return timestamp.isAfter(fromTime);
        } catch (Exception e) {
            /*
             * אם timestamp לא נקרא טוב, בשלב ראשון לא נכשיל את כל ה־Processor.
             */
            return true;
        }
    }


}