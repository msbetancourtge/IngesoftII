package com.clickmunch.RestaurantService.store.dto;

import com.clickmunch.RestaurantService.menu.dto.Dessert;
import com.clickmunch.RestaurantService.menu.dto.Drink;
import com.clickmunch.RestaurantService.menu.dto.Plate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("stores")
public record Store(
        @Id
        Integer id,
        @NotEmpty
        String name,
        String alias,
        @NotEmpty
        @Email
        String email,
        String password,
        String address,
        Double latitude,
        Double longitude,
        @MappedCollection(idColumn = "store_id")
        List<Plate> plates,
        @MappedCollection(idColumn = "store_id")
        List<Drink> drinks,
        @MappedCollection(idColumn = "store_id")
        List<Dessert> desserts

) {
}
