package com.alerthub.processorservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatformInformationDto {

    private Long id;
    private String timestamp;
    private String ownerId;
    private String project;
    private String tag;
    private String label;
    private String developerId;
    private String taskNumber;
    private String environment;
    private String userStory;
    private Integer taskPoint;
    private String sprint;
    private String provider;
    private String sourceFileName;
}