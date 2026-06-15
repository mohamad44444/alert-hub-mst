package com.alerthub.loaderservice.service;

import com.alerthub.loaderservice.entity.PlatformInformation;
import com.alerthub.loaderservice.repository.PlatformInformationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformInformationService {

    private final PlatformInformationRepository repository;

    public PlatformInformationService(PlatformInformationRepository repository) {
        this.repository = repository;
    }

    public List<PlatformInformation> getAll() {
        return repository.findAll();
    }

    public PlatformInformation getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Platform information not found with id: " + id));
    }

    public List<PlatformInformation> getByProvider(String provider) {
        return repository.findByProvider(provider);
    }

    public List<PlatformInformation> getByDeveloperId(String developerId) {
        return repository.findByDeveloperId(developerId);
    }

    public List<PlatformInformation> getByLabel(String label) {
        return repository.findByLabel(label);
    }

    public PlatformInformation create(PlatformInformation platformInformation) {
        return repository.save(platformInformation);
    }

    public void delete(Long id) {
        PlatformInformation existing = getById(id);
        repository.delete(existing);
    }
}