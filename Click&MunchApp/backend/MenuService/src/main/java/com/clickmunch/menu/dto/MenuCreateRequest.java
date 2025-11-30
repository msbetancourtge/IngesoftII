package com.clickmunch.menu.dto;

import java.util.List;

public record MenuCreateRequest(
        Integer storeId,
        List<PlateRequest> plates,
        List<DrinkRequest> drinks,
        List<DessertRequest> desserts
) {
}