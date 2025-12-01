package com.clickmunch.RestaurantService.store.dto;

import com.clickmunch.RestaurantService.menu.dto.Dessert;
import com.clickmunch.RestaurantService.menu.dto.Drink;
import com.clickmunch.RestaurantService.menu.dto.Plate;

import java.util.List;

public record StoreMenuResponse(
    Integer id,
    String name,
    String alias,
    List<Plate> plates,
    List<Drink> drinks,
    List<Dessert> desserts
) {}