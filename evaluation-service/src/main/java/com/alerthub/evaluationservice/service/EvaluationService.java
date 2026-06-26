package com.alerthub.evaluationservice.service;

import com.alerthub.evaluationservice.dto.DeveloperLabelCountDto;
import com.alerthub.evaluationservice.dto.LabelAggregateDto;
import com.alerthub.evaluationservice.dto.TaskAmountDto;
import com.alerthub.evaluationservice.repository.PlatformInformationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EvaluationService {

    private final PlatformInformationRepository repository;

    public EvaluationService(PlatformInformationRepository repository) {
        this.repository = repository;
    }

    public DeveloperLabelCountDto getDeveloperWithMostLabel(String label, Integer since) {
        validateSince(since);

        LocalDateTime sinceTime = LocalDateTime.now().minusDays(since);

        List<DeveloperLabelCountDto> results =
                repository.findDeveloperWithMostLabel(label, sinceTime);

        if (results.isEmpty()) {
            return new DeveloperLabelCountDto("No developer found", 0L);
        }

        return results.get(0);
    }

    public List<LabelAggregateDto> getLabelAggregateByDeveloper(String developerId, Integer since) {
        validateSince(since);

        LocalDateTime sinceTime = LocalDateTime.now().minusDays(since);
        return repository.getLabelAggregateByDeveloper(developerId, sinceTime);
    }

    public TaskAmountDto getTaskAmountByDeveloper(String developerId, Integer since) {
        validateSince(since);

        LocalDateTime sinceTime = LocalDateTime.now().minusDays(since);
        Long count = repository.countTasksByDeveloper(developerId, sinceTime);

        return new TaskAmountDto(developerId, count);
    }

    private void validateSince(Integer since) {
        if (since == null || since < 1) {
            throw new IllegalArgumentException("Since must be at least 1 day");
        }
    }
}