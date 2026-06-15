package com.alerthub.processorservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricDto {

    private Long id;
    private Long userId;
    private String name;
    private String label;
    private Double threshold;
    private Integer timeFrameHours;
}