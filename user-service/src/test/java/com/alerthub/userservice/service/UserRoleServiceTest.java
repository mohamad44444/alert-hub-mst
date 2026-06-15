package com.alerthub.userservice.service;

import com.alerthub.userservice.entity.UserRole;
import com.alerthub.userservice.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserRoleService userRoleService;

    @Test
    void assignRoleToUser_shouldSaveUserRole_whenUserAndRoleAreValidAndNotExists() {
        UserRole userRole = UserRole.builder()
                .userId(1L)
                .roleId(2L)
                .build();

        UserRole savedUserRole = UserRole.builder()
                .id(1L)
                .userId(1L)
                .roleId(2L)
                .build();

        when(userRoleRepository.findByUserIdAndRoleId(1L, 2L))
                .thenReturn(Optional.empty());

        when(userRoleRepository.save(userRole))
                .thenReturn(savedUserRole);

        UserRole result = userRoleService.assignRoleToUser(userRole);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getUserId());
        assertEquals(2L, result.getRoleId());

        verify(userRoleRepository).findByUserIdAndRoleId(1L, 2L);
        verify(userRoleRepository).save(userRole);
    }

    @Test
    void assignRoleToUser_shouldThrowException_whenUserIdIsNull() {
        UserRole userRole = UserRole.builder()
                .userId(null)
                .roleId(2L)
                .build();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userRoleService.assignRoleToUser(userRole);
        });

        assertEquals("User ID and Role ID cannot be empty", exception.getMessage());

        verify(userRoleRepository, never()).save(any(UserRole.class));
    }

    @Test
    void assignRoleToUser_shouldThrowException_whenRoleIdIsNull() {
        UserRole userRole = UserRole.builder()
                .userId(1L)
                .roleId(null)
                .build();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userRoleService.assignRoleToUser(userRole);
        });

        assertEquals("User ID and Role ID cannot be empty", exception.getMessage());

        verify(userRoleRepository, never()).save(any(UserRole.class));
    }

    @Test
    void assignRoleToUser_shouldThrowException_whenUserAlreadyHasRole() {
        UserRole userRole = UserRole.builder()
                .userId(1L)
                .roleId(2L)
                .build();

        when(userRoleRepository.findByUserIdAndRoleId(1L, 2L))
                .thenReturn(Optional.of(userRole));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userRoleService.assignRoleToUser(userRole);
        });

        assertEquals("This user already has this role", exception.getMessage());

        verify(userRoleRepository).findByUserIdAndRoleId(1L, 2L);
        verify(userRoleRepository, never()).save(any(UserRole.class));
    }
}