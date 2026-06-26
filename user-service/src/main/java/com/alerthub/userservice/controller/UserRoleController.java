package com.alerthub.userservice.controller;

import com.alerthub.userservice.entity.UserRole;
import com.alerthub.userservice.service.UserRoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping
    public UserRole assignRoleToUser(@RequestBody UserRole userRole) {
        return userRoleService.assignRoleToUser(userRole);
    }

    @GetMapping
    public List<UserRole> getAllUserRoles() {
        return userRoleService.getAllUserRoles();
    }

    @GetMapping("/user/{userId}")
    public List<UserRole> getRolesByUserId(@PathVariable Long userId) {
        return userRoleService.getRolesByUserId(userId);
    }
}