package com.clickmunch.menu.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("drinks")
public record Drink(
        @Id
        Integer id,
        @Column("store_id")
        Integer storeId,
        String name,
        String description,
        Double price,
        String image
) {
}