package com.alerthub.evaluationservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperLabelCountDto {

    private String developerId;
    private Long count;
}