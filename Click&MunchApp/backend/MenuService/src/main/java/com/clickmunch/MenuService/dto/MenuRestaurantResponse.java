package com.clickmunch.MenuService.dto;

import com.clickmunch.MenuService.entity.MenuItem;

import java.util.List;

public record MenuRestaurantResponse(
    Integer storeId,
    List<MenuItem>menuItems
){
}
