package com.alerthub.userservice.service;

import com.alerthub.userservice.entity.UserRole;
import com.alerthub.userservice.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRole assignRoleToUser(UserRole userRole) {

        if (userRole.getUserId() == null || userRole.getRoleId() == null) {
            throw new RuntimeException("User ID and Role ID cannot be empty");
        }

        if (userRoleRepository
                .findByUserIdAndRoleId(userRole.getUserId(), userRole.getRoleId())
                .isPresent()) {
            throw new RuntimeException("This user already has this role");
        }

        return userRoleRepository.save(userRole);
    }

    public List<UserRole> getAllUserRoles() {
        return userRoleRepository.findAll();
    }

    public List<UserRole> getRolesByUserId(Long userId) {
        return userRoleRepository.findByUserId(userId);
    }
}