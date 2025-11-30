package com.clickmunch.menu.dto;

import java.util.List;

public record StoreMenuResponse(
        Integer storeId,
        List<Plate> plates,
        List<Drink> drinks,
        List<Dessert> desserts
) {
}