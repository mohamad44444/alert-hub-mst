package com.alerthub.userservice.service;

import com.alerthub.userservice.entity.Role;
import com.alerthub.userservice.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(Role role) {

        if (role.getRole() == null || role.getRole().trim().isEmpty()) {
            throw new RuntimeException("Role name cannot be empty");
        }

        if (roleRepository.findByRole(role.getRole()).isPresent()) {
            throw new RuntimeException("Role already exists");
        }

        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}