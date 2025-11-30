package com.clickmunch.RestaurantService.menu.dto;

public record DessertRequest(
        String name,
        String description,
        Double price,
        String image
) {
}
