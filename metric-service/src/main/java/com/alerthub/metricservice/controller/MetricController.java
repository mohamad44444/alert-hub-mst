package com.alerthub.metricservice.controller;

import com.alerthub.metricservice.entity.Metric;
import com.alerthub.metricservice.service.MetricService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
public class MetricController {

    private final MetricService metricService;

    public MetricController(MetricService metricService) {
        this.metricService = metricService;
    }

    @GetMapping
    public List<Metric> getAllMetrics() {
        return metricService.getAllMetrics();
    }

    @GetMapping("/{id}")
    public Metric getMetricById(@PathVariable Long id) {
        return metricService.getMetricById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Metric> getMetricsByUserId(@PathVariable Long userId) {
        return metricService.getMetricsByUserId(userId);
    }

    @PostMapping
    public Metric createMetric(@Valid @RequestBody Metric metric) {
        return metricService.createMetric(metric);
    }

    @PutMapping("/{id}")
    public Metric updateMetric(@PathVariable Long id, @Valid @RequestBody Metric metric) {
        return metricService.updateMetric(id, metric);
    }

    @DeleteMapping("/{id}")
    public String deleteMetric(@PathVariable Long id) {
        metricService.deleteMetric(id);
        return "Metric deleted successfully";
    }
}