package com.alerthub.metricservice.service;

import com.alerthub.metricservice.entity.Metric;
import com.alerthub.metricservice.repository.MetricRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MetricServiceTest {

    private MetricRepository metricRepository;
    private MetricService metricService;

    @BeforeEach
    void setUp() {
        metricRepository = mock(MetricRepository.class);
        metricService = new MetricService(metricRepository);
    }

    @Test
    void getAllMetrics_shouldReturnAllMetrics() {
        Metric metric1 = createMetric(1L);
        Metric metric2 = createMetric(2L);

        when(metricRepository.findAll()).thenReturn(List.of(metric1, metric2));

        List<Metric> result = metricService.getAllMetrics();

        assertEquals(2, result.size());
        verify(metricRepository, times(1)).findAll();
    }

    @Test
    void getMetricById_whenMetricExists_shouldReturnMetric() {
        Metric metric = createMetric(1L);

        when(metricRepository.findById(1L)).thenReturn(Optional.of(metric));

        Metric result = metricService.getMetricById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("bug_10_12", result.getName());
        verify(metricRepository, times(1)).findById(1L);
    }

    @Test
    void getMetricById_whenMetricDoesNotExist_shouldThrowException() {
        when(metricRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> metricService.getMetricById(99L)
        );

        assertEquals("Metric not found with id: 99", exception.getMessage());
        verify(metricRepository, times(1)).findById(99L);
    }

    @Test
    void getMetricsByUserId_shouldReturnUserMetrics() {
        Metric metric = createMetric(1L);

        when(metricRepository.findByUserId(1001L)).thenReturn(List.of(metric));

        List<Metric> result = metricService.getMetricsByUserId(1001L);

        assertEquals(1, result.size());
        assertEquals(1001L, result.get(0).getUserId());
        verify(metricRepository, times(1)).findByUserId(1001L);
    }

    @Test
    void createMetric_shouldSaveMetric() {
        Metric metric = createMetric(null);
        Metric savedMetric = createMetric(1L);

        when(metricRepository.save(metric)).thenReturn(savedMetric);

        Metric result = metricService.createMetric(metric);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("bug", result.getLabel());
        verify(metricRepository, times(1)).save(metric);
    }

    @Test
    void updateMetric_whenMetricExists_shouldUpdateAndSaveMetric() {
        Metric existingMetric = createMetric(1L);

        Metric updatedMetric = new Metric();
        updatedMetric.setUserId(1001L);
        updatedMetric.setName("help_1_4");
        updatedMetric.setLabel("help_wanted");
        updatedMetric.setThreshold(1.0);
        updatedMetric.setTimeFrameHours(4);

        when(metricRepository.findById(1L)).thenReturn(Optional.of(existingMetric));
        when(metricRepository.save(existingMetric)).thenReturn(existingMetric);

        Metric result = metricService.updateMetric(1L, updatedMetric);

        assertEquals("help_1_4", result.getName());
        assertEquals("help_wanted", result.getLabel());
        assertEquals(1.0, result.getThreshold());
        assertEquals(4, result.getTimeFrameHours());

        verify(metricRepository, times(1)).findById(1L);
        verify(metricRepository, times(1)).save(existingMetric);
    }

    @Test
    void deleteMetric_whenMetricExists_shouldDeleteMetric() {
        Metric metric = createMetric(1L);

        when(metricRepository.findById(1L)).thenReturn(Optional.of(metric));

        metricService.deleteMetric(1L);

        verify(metricRepository, times(1)).findById(1L);
        verify(metricRepository, times(1)).delete(metric);
    }

    private Metric createMetric(Long id) {
        Metric metric = new Metric();
        metric.setId(id);
        metric.setUserId(1001L);
        metric.setName("bug_10_12");
        metric.setLabel("bug");
        metric.setThreshold(10.0);
        metric.setTimeFrameHours(12);
        return metric;
    }
}