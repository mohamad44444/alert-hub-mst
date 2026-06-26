package com.alerthub.evaluationservice.service;

import com.alerthub.evaluationservice.dto.DeveloperLabelCountDto;
import com.alerthub.evaluationservice.dto.LabelAggregateDto;
import com.alerthub.evaluationservice.dto.TaskAmountDto;
import com.alerthub.evaluationservice.repository.PlatformInformationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EvaluationServiceTest {

    private PlatformInformationRepository repository;
    private EvaluationService evaluationService;

    @BeforeEach
    void setUp() {
        repository = mock(PlatformInformationRepository.class);
        evaluationService = new EvaluationService(repository);
    }

    @Test
    void getDeveloperWithMostLabel_whenResultExists_shouldReturnFirstDeveloper() {
        DeveloperLabelCountDto dto = new DeveloperLabelCountDto("dev-1", 5L);

        when(repository.findDeveloperWithMostLabel(eq("bug"), any(LocalDateTime.class)))
                .thenReturn(List.of(dto));

        DeveloperLabelCountDto result = evaluationService.getDeveloperWithMostLabel("bug", 7);

        assertNotNull(result);
        assertEquals("dev-1", result.getDeveloperId());
        assertEquals(5L, result.getCount());

        verify(repository, times(1))
                .findDeveloperWithMostLabel(eq("bug"), any(LocalDateTime.class));
    }

    @Test
    void getDeveloperWithMostLabel_whenNoResult_shouldReturnNoDeveloperFound() {
        when(repository.findDeveloperWithMostLabel(eq("bug"), any(LocalDateTime.class)))
                .thenReturn(List.of());

        DeveloperLabelCountDto result = evaluationService.getDeveloperWithMostLabel("bug", 7);

        assertNotNull(result);
        assertEquals("No developer found", result.getDeveloperId());
        assertEquals(0L, result.getCount());

        verify(repository, times(1))
                .findDeveloperWithMostLabel(eq("bug"), any(LocalDateTime.class));
    }

    @Test
    void getLabelAggregateByDeveloper_shouldReturnLabelAggregates() {
        LabelAggregateDto bug = new LabelAggregateDto("bug", 3L);
        LabelAggregateDto question = new LabelAggregateDto("question", 2L);

        when(repository.getLabelAggregateByDeveloper(eq("dev-1"), any(LocalDateTime.class)))
                .thenReturn(List.of(bug, question));

        List<LabelAggregateDto> result =
                evaluationService.getLabelAggregateByDeveloper("dev-1", 7);

        assertEquals(2, result.size());
        assertEquals("bug", result.get(0).getLabel());
        assertEquals(3L, result.get(0).getCount());

        verify(repository, times(1))
                .getLabelAggregateByDeveloper(eq("dev-1"), any(LocalDateTime.class));
    }

    @Test
    void getTaskAmountByDeveloper_shouldReturnTaskAmount() {
        when(repository.countTasksByDeveloper(eq("dev-1"), any(LocalDateTime.class)))
                .thenReturn(10L);

        TaskAmountDto result = evaluationService.getTaskAmountByDeveloper("dev-1", 7);

        assertNotNull(result);
        assertEquals("dev-1", result.getDeveloperId());
        assertEquals(10L, result.getTaskAmount());

        verify(repository, times(1))
                .countTasksByDeveloper(eq("dev-1"), any(LocalDateTime.class));
    }
}