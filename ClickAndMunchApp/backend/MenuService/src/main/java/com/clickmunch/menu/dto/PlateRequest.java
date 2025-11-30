package com.clickmunch.menu.dto;

public record PlateRequest(
        String name,
        String description,
        Double price,
        String image
) {
}