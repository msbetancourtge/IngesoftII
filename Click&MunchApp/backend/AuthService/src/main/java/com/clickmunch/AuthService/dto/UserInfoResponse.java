package com.clickmunch.AuthService.dto;

import com.clickmunch.AuthService.entity.Role;

public record UserInfoResponse(
        String username,
        String role
) {
}
