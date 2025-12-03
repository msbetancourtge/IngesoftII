package com.clickmunch.MenuService.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("menu_categories")
public class MenuCategory {
    @Id
    private Long id;
    @Column("restaurant_id")
    private Long restaurantId;
    private String name;
}
