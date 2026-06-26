package com.alerthub.loaderservice.service;

import com.alerthub.loaderservice.entity.PlatformInformation;
import com.alerthub.loaderservice.repository.PlatformInformationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlatformInformationServiceTest {

    private PlatformInformationRepository repository;
    private PlatformInformationService service;

    @BeforeEach
    void setUp() {
        repository = mock(PlatformInformationRepository.class);
        service = new PlatformInformationService(repository);
    }

    @Test
    void getAll_shouldReturnAllPlatformInformation() {
        PlatformInformation item1 = createPlatformInformation(1L);
        PlatformInformation item2 = createPlatformInformation(2L);

        when(repository.findAll()).thenReturn(List.of(item1, item2));

        List<PlatformInformation> result = service.getAll();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getById_whenExists_shouldReturnPlatformInformation() {
        PlatformInformation item = createPlatformInformation(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(item));

        PlatformInformation result = service.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("GitHub", result.getProvider());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getById_whenNotExists_shouldThrowException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.getById(99L)
        );

        assertEquals("Platform information not found with id: 99", exception.getMessage());
        verify(repository, times(1)).findById(99L);
    }

    @Test
    void getByProvider_shouldReturnProviderItems() {
        PlatformInformation item = createPlatformInformation(1L);

        when(repository.findByProvider("GitHub")).thenReturn(List.of(item));

        List<PlatformInformation> result = service.getByProvider("GitHub");

        assertEquals(1, result.size());
        assertEquals("GitHub", result.get(0).getProvider());
        verify(repository, times(1)).findByProvider("GitHub");
    }

    @Test
    void getByDeveloperId_shouldReturnDeveloperItems() {
        PlatformInformation item = createPlatformInformation(1L);

        when(repository.findByDeveloperId("dev-1")).thenReturn(List.of(item));

        List<PlatformInformation> result = service.getByDeveloperId("dev-1");

        assertEquals(1, result.size());
        assertEquals("dev-1", result.get(0).getDeveloperId());
        verify(repository, times(1)).findByDeveloperId("dev-1");
    }

    @Test
    void getByLabel_shouldReturnLabelItems() {
        PlatformInformation item = createPlatformInformation(1L);

        when(repository.findByLabel("bug")).thenReturn(List.of(item));

        List<PlatformInformation> result = service.getByLabel("bug");

        assertEquals(1, result.size());
        assertEquals("bug", result.get(0).getLabel());
        verify(repository, times(1)).findByLabel("bug");
    }

    @Test
    void create_shouldSavePlatformInformation() {
        PlatformInformation item = createPlatformInformation(null);
        PlatformInformation savedItem = createPlatformInformation(1L);

        when(repository.save(item)).thenReturn(savedItem);

        PlatformInformation result = service.create(item);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("GitHub", result.getProvider());
        verify(repository, times(1)).save(item);
    }

    @Test
    void delete_whenExists_shouldDeletePlatformInformation() {
        PlatformInformation item = createPlatformInformation(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(item));

        service.delete(1L);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(item);
    }

    private PlatformInformation createPlatformInformation(Long id) {
        PlatformInformation item = new PlatformInformation();
        item.setId(id);
        item.setOwnerId("owner-1");
        item.setProject("Alert Hub");
        item.setTag("backend");
        item.setLabel("bug");
        item.setDeveloperId("dev-1");
        item.setTaskNumber("TASK-1");
        item.setEnvironment("prod");
        item.setUserStory("As a manager I want alerts");
        item.setTaskPoint(3);
        item.setSprint("Sprint 1");
        item.setProvider("GitHub");
        item.setSourceFileName("github_2026_06_14T10_00_00");
        return item;
    }
}