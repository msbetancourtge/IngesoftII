package com.clickmunch.RestaurantService.menu.dto;

public record DrinkRequest(
        String name,
        String description,
        Double price,
        String image
) {
}
