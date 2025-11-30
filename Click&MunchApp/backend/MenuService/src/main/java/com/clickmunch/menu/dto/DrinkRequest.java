package com.clickmunch.menu.dto;

public record DrinkRequest(
        String name,
        String description,
        Double price,
        String image
) {
}