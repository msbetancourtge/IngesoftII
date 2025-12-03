package com.clickmunch.MenuService.controller;

import com.clickmunch.MenuService.dto.MenuCategoryRequest;
import com.clickmunch.MenuService.dto.MenuCreateRequest;
import com.clickmunch.MenuService.dto.MenuItemRequest;
import com.clickmunch.MenuService.dto.MenuRestaurantResponse;
import com.clickmunch.MenuService.entity.MenuCategory;
import com.clickmunch.MenuService.entity.MenuItem;
import com.clickmunch.MenuService.service.MenuService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    // CRUD
    // Create a menu item category in a specific restaurant
    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuCategory createMenuCategory(@Valid @RequestBody MenuCategoryRequest req) {
        return menuService.createMenuCategory(req);
    }

    // Create a menu item in a specific category
    @PostMapping("/categories/{categoryId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItem createMenuItem(@PathVariable Long categoryId,@Valid @RequestBody MenuItemRequest req) {
        return menuService.createMenuItem(categoryId, req);
    }

    // Get a single menu category
    @GetMapping("/categories/{categoryId}")
    public MenuCategory getMenuCategory(@PathVariable Long categoryId) {
        try {
            return menuService.findMenuCategoryById(categoryId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu category not found");
        }
    }

    // Get a single menu item
    @GetMapping("/items/{itemId}")
    public MenuItem getMenuItem(@PathVariable Long itemId) {
        try {
            return menuService.findMenuItemById(itemId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu item not found");
        }
    }

    // Update a menu categopru
    @PutMapping("/categories/{categoryId}")
    public MenuCategory updateMenuCategory(@PathVariable Long categoryId, @RequestBody MenuCategoryRequest req) {
        try {
            return menuService.updateMenuCategory(categoryId, req);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Update a menu item
    @PutMapping("/items/{itemId}")
    public MenuItem updateMenuItem(@PathVariable Long itemId, @RequestBody MenuItemRequest req) {
        try {
            return menuService.updateMenuItem(itemId, req);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Delete a menu category
    @DeleteMapping("/categories/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuCategory(@PathVariable Long categoryId) {
        menuService.deleteMenuCategory(categoryId);
    }

    // Delete a menu item
    @DeleteMapping("/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuItem(@PathVariable Long itemId) {
        menuService.deleteMenuItem(itemId);
    }

    // Restaurant-specific operations
    // Create full menu for a restaurant (categories + items)
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuRestaurantResponse createFullMenu(@Valid @RequestBody MenuCreateRequest request) {
        try {
            return menuService.createFullMenu(request);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Get full menu for a restaurant
    @GetMapping("/restaurants/{restaurantId}")
    public MenuRestaurantResponse getMenuByRestaurantId(@PathVariable Long restaurantId) {
        return menuService.getMenuByRestaurantId(restaurantId);
    }

    // Get all items for a restaurant (lookups via categories' restaurant_id)
    @GetMapping("/restaurants/{restaurantId}/items")
    public List<MenuItem> getMenuItemsByRestaurantId(@PathVariable Long restaurantId) {
        try {
            return menuService.findMenuItemsByRestaurantId(restaurantId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Delete all menu categories & items for a restaurant
    @DeleteMapping("/restaurants/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuByRestaurantId(@PathVariable Long restaurantId) {
        menuService.deleteMenuByRestaurantId(restaurantId);
    }
}
