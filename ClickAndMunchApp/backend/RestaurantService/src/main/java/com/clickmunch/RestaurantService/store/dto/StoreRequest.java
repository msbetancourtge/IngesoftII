package com.clickmunch.RestaurantService.store.dto;

import com.clickmunch.RestaurantService.menu.dto.DessertRequest;
import com.clickmunch.RestaurantService.menu.dto.DrinkRequest;
import com.clickmunch.RestaurantService.menu.dto.PlateRequest;

import java.util.List;

public record StoreRequest(
        Integer storeId,
        List<PlateRequest> plates,
        List<DrinkRequest> drinks,
        List<DessertRequest> desserts
) {
}
