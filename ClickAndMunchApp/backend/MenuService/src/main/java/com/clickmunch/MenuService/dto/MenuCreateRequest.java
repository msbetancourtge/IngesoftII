package com.clickmunch.MenuService.dto;

import java.math.BigDecimal;
import java.util.List;

public record MenuCreateRequest(
    Long restaurantId,
    List<CategoryRequest> categories
) {
    public record CategoryRequest(
        String name,
        List<ItemRequest> items
    ) {}

    public record ItemRequest(
        String name,
        String description,
        BigDecimal price,
        String imageUrl
    ) {}
}