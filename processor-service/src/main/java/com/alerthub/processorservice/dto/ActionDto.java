package com.alerthub.processorservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionDto {

    private Long id;
    private Long userId;
    private String name;
    private String conditionJson;
    private String to;
    private String actionType;
    private String runOnTime;
    private String runOnDay;
    private String message;
    private Boolean isEnabled;
    private Boolean isDeleted;
}