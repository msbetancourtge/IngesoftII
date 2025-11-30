package com.clickmunch.menu.dto;

public record DessertRequest(
        String name,
        String description,
        Double price,
        String image
) {
}