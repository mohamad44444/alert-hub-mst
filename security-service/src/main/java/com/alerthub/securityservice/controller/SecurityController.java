package com.alerthub.securityservice.controller;

import com.alerthub.securityservice.dto.GrantPermissionRequest;
import com.alerthub.securityservice.dto.PermissionCheckResponse;
import com.alerthub.securityservice.dto.PermissionResponse;
import com.alerthub.securityservice.service.SecurityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/permissions/{userId}")
    public ResponseEntity<List<String>> getUserPermissions(@PathVariable Long userId) {
        return ResponseEntity.ok(securityService.getUserPermissions(userId));
    }

    @GetMapping("/has-permission")
    public ResponseEntity<PermissionCheckResponse> hasPermission(
            @RequestParam Long userId,
            @RequestParam String permission
    ) {
        return ResponseEntity.ok(securityService.hasPermission(userId, permission));
    }

    @PostMapping("/permissions/grant")
    public ResponseEntity<PermissionResponse> grantPermission(@RequestBody GrantPermissionRequest request) {
        return ResponseEntity.ok(securityService.grantPermission(request));
    }

    @DeleteMapping("/permissions/revoke")
    public ResponseEntity<PermissionResponse> revokePermission(@RequestBody GrantPermissionRequest request) {
        return ResponseEntity.ok(securityService.revokePermission(request));
    }
}