package com.clickmunch.RestaurantService.store.dto;

public record StoreUpdateRequest(
        String name,
        String alias,
        String password,
        String address,
        Double latitude,
        Double longitude
) {}
