package com.clickmunch.MenuService.entity;

import lombok.Data;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("menu_items")
public class MenuItem {
    @Id
    private Long id;
    @Column("category_id")
    private Long categoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    
}
