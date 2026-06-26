package com.alerthub.userservice.service;

import com.alerthub.userservice.entity.Role;
import com.alerthub.userservice.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void createRole_shouldSaveRole_whenRoleIsValidAndNotExists() {
        Role role = Role.builder()
                .role("createAction")
                .build();

        Role savedRole = Role.builder()
                .id(1L)
                .role("createAction")
                .build();

        when(roleRepository.findByRole("createAction")).thenReturn(Optional.empty());
        when(roleRepository.save(role)).thenReturn(savedRole);

        Role result = roleService.createRole(role);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("createAction", result.getRole());

        verify(roleRepository).findByRole("createAction");
        verify(roleRepository).save(role);
    }

    @Test
    void createRole_shouldThrowException_whenRoleNameIsEmpty() {
        Role role = Role.builder()
                .role("")
                .build();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            roleService.createRole(role);
        });

        assertEquals("Role name cannot be empty", exception.getMessage());

        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void createRole_shouldThrowException_whenRoleAlreadyExists() {
        Role role = Role.builder()
                .role("createAction")
                .build();

        when(roleRepository.findByRole("createAction")).thenReturn(Optional.of(role));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            roleService.createRole(role);
        });

        assertEquals("Role already exists", exception.getMessage());

        verify(roleRepository).findByRole("createAction");
        verify(roleRepository, never()).save(any(Role.class));
    }
}