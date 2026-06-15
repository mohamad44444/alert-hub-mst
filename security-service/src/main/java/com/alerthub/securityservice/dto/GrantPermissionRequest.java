package com.alerthub.securityservice.dto;

import lombok.Data;

@Data
public class GrantPermissionRequest {
    private Long userId;
    private String permission;
}