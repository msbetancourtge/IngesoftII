package com.clickmunch.RestaurantService.store.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record StoreRegisterRequest(
    String name,
    @NotEmpty
    String alias,
    @NotEmpty
    @Email
    String email,
    String password,
    String address,
    Double latitude,
    Double longitude
) {}