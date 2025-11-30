package com.clickmunch.RestaurantService.menu.dto;

public record PlateRequest(
        String name,
        String description,
        Double price,
        String image
) {
}
