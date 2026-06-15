package com.alerthub.securityservice.service;

import com.alerthub.securityservice.dto.GrantPermissionRequest;
import com.alerthub.securityservice.dto.PermissionCheckResponse;
import com.alerthub.securityservice.dto.PermissionResponse;
import com.alerthub.securityservice.entity.Role;
import com.alerthub.securityservice.entity.UserRole;
import com.alerthub.securityservice.repository.RoleRepository;
import com.alerthub.securityservice.repository.UserRepository;
import com.alerthub.securityservice.repository.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityServiceTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserRoleRepository userRoleRepository;
    private SecurityService securityService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        userRoleRepository = mock(UserRoleRepository.class);

        securityService = new SecurityService(
                userRepository,
                roleRepository,
                userRoleRepository
        );
    }

    @Test
    void getUserPermissions_whenUserExists_shouldReturnPermissions() {
        UserRole userRole = UserRole.builder()
                .id(1L)
                .userId(1001L)
                .roleId(1L)
                .build();

        Role role = Role.builder()
                .id(1L)
                .role("createAction")
                .build();

        when(userRepository.existsById(1001L)).thenReturn(true);
        when(userRoleRepository.findByUserId(1001L)).thenReturn(List.of(userRole));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        List<String> result = securityService.getUserPermissions(1001L);

        assertEquals(1, result.size());
        assertEquals("createAction", result.get(0));

        verify(userRepository).existsById(1001L);
        verify(userRoleRepository).findByUserId(1001L);
        verify(roleRepository).findById(1L);
    }

    @Test
    void hasPermission_whenUserHasPermission_shouldReturnAllowedTrue() {
        Role role = Role.builder()
                .id(1L)
                .role("createAction")
                .build();

        UserRole userRole = UserRole.builder()
                .id(1L)
                .userId(1001L)
                .roleId(1L)
                .build();

        when(userRepository.existsById(1001L)).thenReturn(true);
        when(roleRepository.findByRole("createAction")).thenReturn(Optional.of(role));
        when(userRoleRepository.findByUserIdAndRoleId(1001L, 1L))
                .thenReturn(Optional.of(userRole));

        PermissionCheckResponse result =
                securityService.hasPermission(1001L, "createAction");

        assertEquals(1001L, result.getUserId());
        assertEquals("createAction", result.getPermission());
        assertTrue(result.isAllowed());

        verify(userRepository).existsById(1001L);
        verify(roleRepository).findByRole("createAction");
        verify(userRoleRepository).findByUserIdAndRoleId(1001L, 1L);
    }

    @Test
    void hasPermission_whenUserDoesNotHavePermission_shouldReturnAllowedFalse() {
        Role role = Role.builder()
                .id(1L)
                .role("createAction")
                .build();

        when(userRepository.existsById(1001L)).thenReturn(true);
        when(roleRepository.findByRole("createAction")).thenReturn(Optional.of(role));
        when(userRoleRepository.findByUserIdAndRoleId(1001L, 1L))
                .thenReturn(Optional.empty());

        PermissionCheckResponse result =
                securityService.hasPermission(1001L, "createAction");

        assertFalse(result.isAllowed());
    }

    @Test
    void grantPermission_whenPermissionDoesNotExistForUser_shouldSavePermission() {
        GrantPermissionRequest request = new GrantPermissionRequest();
        request.setUserId(1001L);
        request.setPermission("createAction");

        Role role = Role.builder()
                .id(1L)
                .role("createAction")
                .build();

        when(userRepository.existsById(1001L)).thenReturn(true);
        when(roleRepository.findByRole("createAction")).thenReturn(Optional.of(role));
        when(userRoleRepository.findByUserIdAndRoleId(1001L, 1L))
                .thenReturn(Optional.empty());

        PermissionResponse result = securityService.grantPermission(request);

        assertEquals("Permission granted successfully", result.getMessage());

        verify(userRoleRepository, times(1)).save(any(UserRole.class));
    }

    @Test
    void grantPermission_whenPermissionAlreadyExists_shouldReturnAlreadyExistsMessage() {
        GrantPermissionRequest request = new GrantPermissionRequest();
        request.setUserId(1001L);
        request.setPermission("createAction");

        Role role = Role.builder()
                .id(1L)
                .role("createAction")
                .build();

        UserRole existing = UserRole.builder()
                .id(1L)
                .userId(1001L)
                .roleId(1L)
                .build();

        when(userRepository.existsById(1001L)).thenReturn(true);
        when(roleRepository.findByRole("createAction")).thenReturn(Optional.of(role));
        when(userRoleRepository.findByUserIdAndRoleId(1001L, 1L))
                .thenReturn(Optional.of(existing));

        PermissionResponse result = securityService.grantPermission(request);

        assertEquals("Permission already exists for this user", result.getMessage());

        verify(userRoleRepository, never()).save(any(UserRole.class));
    }

    @Test
    void revokePermission_whenPermissionExists_shouldDeletePermission() {
        GrantPermissionRequest request = new GrantPermissionRequest();
        request.setUserId(1001L);
        request.setPermission("createAction");

        Role role = Role.builder()
                .id(1L)
                .role("createAction")
                .build();

        UserRole existing = UserRole.builder()
                .id(1L)
                .userId(1001L)
                .roleId(1L)
                .build();

        when(userRepository.existsById(1001L)).thenReturn(true);
        when(roleRepository.findByRole("createAction")).thenReturn(Optional.of(role));
        when(userRoleRepository.findByUserIdAndRoleId(1001L, 1L))
                .thenReturn(Optional.of(existing));

        PermissionResponse result = securityService.revokePermission(request);

        assertEquals("Permission revoked successfully", result.getMessage());

        verify(userRoleRepository, times(1))
                .deleteByUserIdAndRoleId(1001L, 1L);
    }

    @Test
    void revokePermission_whenPermissionDoesNotExist_shouldReturnMessage() {
        GrantPermissionRequest request = new GrantPermissionRequest();
        request.setUserId(1001L);
        request.setPermission("createAction");

        Role role = Role.builder()
                .id(1L)
                .role("createAction")
                .build();

        when(userRepository.existsById(1001L)).thenReturn(true);
        when(roleRepository.findByRole("createAction")).thenReturn(Optional.of(role));
        when(userRoleRepository.findByUserIdAndRoleId(1001L, 1L))
                .thenReturn(Optional.empty());

        PermissionResponse result = securityService.revokePermission(request);

        assertEquals("Permission does not exist for this user", result.getMessage());

        verify(userRoleRepository, never())
                .deleteByUserIdAndRoleId(anyLong(), anyLong());
    }

    @Test
    void getUserPermissions_whenUserDoesNotExist_shouldThrowException() {
        when(userRepository.existsById(999L)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> securityService.getUserPermissions(999L)
        );

        assertEquals("User not found: 999", exception.getMessage());
    }

    @Test
    void grantPermission_whenPermissionIsBlank_shouldThrowException() {
        GrantPermissionRequest request = new GrantPermissionRequest();
        request.setUserId(1001L);
        request.setPermission("");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> securityService.grantPermission(request)
        );

        assertEquals("Permission is required", exception.getMessage());
    }
}