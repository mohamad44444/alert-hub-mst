package com.alerthub.securityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PermissionCheckResponse {
    private Long userId;
    private String permission;
    private boolean allowed;
}