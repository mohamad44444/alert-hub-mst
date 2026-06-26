package com.alerthub.loaderservice.controller;

import com.alerthub.loaderservice.entity.PlatformInformation;
import com.alerthub.loaderservice.service.PlatformInformationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/platform-information")
public class PlatformInformationController {

    private final PlatformInformationService service;

    public PlatformInformationController(PlatformInformationService service) {
        this.service = service;
    }

    @GetMapping
    public List<PlatformInformation> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public PlatformInformation getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/provider/{provider}")
    public List<PlatformInformation> getByProvider(@PathVariable String provider) {
        return service.getByProvider(provider);
    }

    @GetMapping("/developer/{developerId}")
    public List<PlatformInformation> getByDeveloperId(@PathVariable String developerId) {
        return service.getByDeveloperId(developerId);
    }

    @GetMapping("/label/{label}")
    public List<PlatformInformation> getByLabel(@PathVariable String label) {
        return service.getByLabel(label);
    }

    @PostMapping
    public PlatformInformation create(@Valid @RequestBody PlatformInformation platformInformation) {
        return service.create(platformInformation);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Platform information deleted successfully";
    }
}