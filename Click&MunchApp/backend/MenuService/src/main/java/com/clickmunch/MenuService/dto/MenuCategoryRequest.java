package com.clickmunch.MenuService.dto;

public record MenuCategoryRequest(
    Long restaurantId,
    String name
) {
}