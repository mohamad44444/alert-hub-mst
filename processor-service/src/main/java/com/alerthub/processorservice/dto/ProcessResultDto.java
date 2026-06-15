package com.alerthub.processorservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProcessResultDto {

    private Long actionId;
    private Boolean conditionSatisfied;
    private String actionType;
    private String recipient;
    private String message;
}