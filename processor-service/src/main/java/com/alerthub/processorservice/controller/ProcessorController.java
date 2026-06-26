package com.alerthub.processorservice.controller;

import com.alerthub.processorservice.dto.ProcessResultDto;
import com.alerthub.processorservice.service.ProcessorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/processor")
public class ProcessorController {

    private final ProcessorService processorService;

    public ProcessorController(ProcessorService processorService) {
        this.processorService = processorService;
    }

    @PostMapping("/actions/{actionId}/process")
    public ProcessResultDto processAction(@PathVariable Long actionId) {
        return processorService.processAction(actionId);
    }
}