package com.alerthub.evaluationservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabelAggregateDto {

    private String label;
    private Long count;
}