package com.alerthub.securityservice.service;

import com.alerthub.securityservice.dto.GrantPermissionRequest;
import com.alerthub.securityservice.dto.PermissionCheckResponse;
import com.alerthub.securityservice.dto.PermissionResponse;
import com.alerthub.securityservice.entity.Role;
import com.alerthub.securityservice.entity.UserRole;
import com.alerthub.securityservice.repository.RoleRepository;
import com.alerthub.securityservice.repository.UserRepository;
import com.alerthub.securityservice.repository.UserRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SecurityService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public SecurityService(UserRepository userRepository,
                           RoleRepository roleRepository,
                           UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public List<String> getUserPermissions(Long userId) {
        validateUserExists(userId);

        List<UserRole> userRoles = userRoleRepository.findByUserId(userId);

        return userRoles.stream()
                .map(userRole -> roleRepository.findById(userRole.getRoleId())
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + userRole.getRoleId()))
                        .getRole())
                .toList();
    }

    public PermissionCheckResponse hasPermission(Long userId, String permission) {
        validateUserExists(userId);

        Role role = roleRepository.findByRole(permission)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found: " + permission));

        boolean allowed = userRoleRepository
                .findByUserIdAndRoleId(userId, role.getId())
                .isPresent();

        return new PermissionCheckResponse(userId, permission, allowed);
    }

    public PermissionResponse grantPermission(GrantPermissionRequest request) {
        validateRequest(request);

        validateUserExists(request.getUserId());

        Role role = roleRepository.findByRole(request.getPermission())
                .orElseThrow(() -> new IllegalArgumentException("Permission not found: " + request.getPermission()));

        boolean alreadyExists = userRoleRepository
                .findByUserIdAndRoleId(request.getUserId(), role.getId())
                .isPresent();

        if (alreadyExists) {
            return new PermissionResponse("Permission already exists for this user");
        }

        UserRole userRole = UserRole.builder()
                .userId(request.getUserId())
                .roleId(role.getId())
                .build();

        userRoleRepository.save(userRole);

        return new PermissionResponse("Permission granted successfully");
    }

    @Transactional
    public PermissionResponse revokePermission(GrantPermissionRequest request) {
        validateRequest(request);

        validateUserExists(request.getUserId());

        Role role = roleRepository.findByRole(request.getPermission())
                .orElseThrow(() -> new IllegalArgumentException("Permission not found: " + request.getPermission()));

        boolean exists = userRoleRepository
                .findByUserIdAndRoleId(request.getUserId(), role.getId())
                .isPresent();

        if (!exists) {
            return new PermissionResponse("Permission does not exist for this user");
        }

        userRoleRepository.deleteByUserIdAndRoleId(request.getUserId(), role.getId());

        return new PermissionResponse("Permission revoked successfully");
    }

    private void validateUserExists(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id is required");
        }

        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
    }

    private void validateRequest(GrantPermissionRequest request) {
        if (request.getUserId() == null) {
            throw new IllegalArgumentException("User id is required");
        }

        if (request.getPermission() == null || request.getPermission().isBlank()) {
            throw new IllegalArgumentException("Permission is required");
        }
    }
}