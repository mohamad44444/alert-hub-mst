package com.alerthub.evaluationservice.controller;

import com.alerthub.evaluationservice.dto.DeveloperLabelCountDto;
import com.alerthub.evaluationservice.dto.LabelAggregateDto;
import com.alerthub.evaluationservice.dto.TaskAmountDto;
import com.alerthub.evaluationservice.service.EvaluationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {

    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping("/developer/most-label")
    public DeveloperLabelCountDto getDeveloperWithMostLabel(
            @RequestParam String label,
            @RequestParam Integer since
    ) {
        return evaluationService.getDeveloperWithMostLabel(label, since);
    }

    @GetMapping("/developer/{developerId}/label-aggregate")
    public List<LabelAggregateDto> getLabelAggregateByDeveloper(
            @PathVariable String developerId,
            @RequestParam Integer since
    ) {
        return evaluationService.getLabelAggregateByDeveloper(developerId, since);
    }

    @GetMapping("/developer/{developerId}/task-amount")
    public TaskAmountDto getTaskAmountByDeveloper(
            @PathVariable String developerId,
            @RequestParam Integer since
    ) {
        return evaluationService.getTaskAmountByDeveloper(developerId, since);
    }
}