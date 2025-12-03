package com.clickmunch.MenuService.service;

import com.clickmunch.MenuService.dto.MenuItemRequest;
import com.clickmunch.MenuService.dto.MenuRestaurantResponse;
import com.clickmunch.MenuService.dto.MenuCreateRequest;
import com.clickmunch.MenuService.entity.*;
import com.clickmunch.MenuService.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuItemRepository menuItemRepository;

    public MenuService(
        MenuCategoryRepository menuCategoryRepository,
        MenuItemRepository menuItemRepository
    ) {
            this.menuCategoryRepository = menuCategoryRepository;
            this.menuItemRepository = menuItemRepository;
        }

    // Get full menu for a restaurant
    public MenuRestaurantResponse getMenuByStoreId(Integer storeId) {
        Long restaurantId = storeId == null ? null : storeId.longValue();

        List<MenuCategory> categories = menuCategoryRepository.findByRestaurantId(restaurantId);
        if (categories == null || categories.isEmpty()) {
            return new MenuRestaurantResponse(storeId, List.of());
        }

        List<Long> categoryIds = categories.stream()
                .map(MenuCategory::getId)
                .collect(Collectors.toList());

        List<MenuItem> items = menuItemRepository.findAllByCategoryIdIn(categoryIds);
        return new MenuRestaurantResponse(storeId, items);
    }

    // Remove all menu items for a restaurant
    @Transactional
    public void deleteMenuByRestaurantId(Long restaurantId) {
        Long resId = restaurantId == null ? null : restaurantId;

        List<MenuCategory> categories = menuCategoryRepository.findByRestaurantId(resId);
        if (categories == null || categories.isEmpty())
            return;

        List<Long> categoryIds = categories.stream()
                .map(MenuCategory::getId)
                .collect(Collectors.toList());

        // Option A: explicitly delete items, then delete categories
        menuItemRepository.deleteAllByCategoryIdIn(categoryIds);
        menuCategoryRepository.deleteAllByRestaurantId(resId);

        // Option B (alternative): just delete categories and rely on FK ON DELETE CASCADE:
        // menuCategoryRepository.deleteAllByRestaurantId(restaurantId);
    }

    // CRUD
    public MenuItem createMenuItem(Long categoryId, MenuItemRequest req) {
        MenuItem item = new MenuItem();
        item.setCategoryId(categoryId == null ? null : categoryId.longValue());
        item.setName(req.name());
        item.setDescription(req.description());
        item.setPrice(req.price());
        item.setImageUrl(req.imageUrl());

        return menuItemRepository.save(item);
    }

    // Create full menu for a restaurant: categories + items
    @Transactional
    public MenuRestaurantResponse createFullMenu(Integer storeId, MenuCreateRequest request) {
        if (request == null || request.categories() == null || request.categories().isEmpty()) {
            return new MenuRestaurantResponse(storeId, List.of());
        }

        if (storeId == null || !storeId.equals(request.storeId())) {
            throw new IllegalArgumentException("storeId mismatch or null");
        }

        // 1) create category entities
        List<MenuCategory> categoriesToSave = request.categories().stream()
                .map(cReq -> {
                    MenuCategory mc = new MenuCategory();
                    mc.setRestaurantId(storeId.longValue());
                    mc.setName(cReq.name());
                    return mc;
                })
                .collect(Collectors.toList());

        Iterable<MenuCategory> savedCatsIterable = menuCategoryRepository.saveAll(categoriesToSave);
        List<MenuCategory> savedCats = new ArrayList<>();
        savedCatsIterable.forEach(savedCats::add);

        // 2) build items with the saved category ids and save
        List<MenuItem> itemsToSave = new ArrayList<>();
        for (int i = 0; i < savedCats.size(); i++) {
            MenuCategory savedCat = savedCats.get(i);
            List<MenuCreateRequest.ItemRequest> itemReqs = request.categories().get(i).items();
            if (itemReqs == null) continue;
            for (MenuCreateRequest.ItemRequest ir : itemReqs) {
                MenuItem mi = new MenuItem();
                mi.setCategoryId(savedCat.getId());
                mi.setName(ir.name());
                mi.setDescription(ir.description());
                mi.setPrice(ir.price());
                mi.setImageUrl(ir.imageUrl());
                itemsToSave.add(mi);
            }
        }

        Iterable<MenuItem> savedItemsIterable = menuItemRepository.saveAll(itemsToSave);
        List<MenuItem> savedItems = new ArrayList<>();
        savedItemsIterable.forEach(savedItems::add);

        // return response with saved items
        return new MenuRestaurantResponse(storeId, savedItems);
    }

    public MenuItem findMenuItemById(Long id) {
        return menuItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu Item not found"));
    }

    public List<MenuItem> findMenuItemsByRestaurantId(Long restaurantId) {
        Long resId = restaurantId == null ? null : restaurantId;

        List<MenuCategory> categories = menuCategoryRepository.findByRestaurantId(resId);
        if (categories == null || categories.isEmpty())
            throw new RuntimeException("Invalid restaurant ID or restaurant has no Item Categries.");

        List<Long> categoryIds = categories.stream()
                .map(MenuCategory::getId)
                .collect(Collectors.toList());     

        return menuItemRepository.findAllByCategoryIdIn(categoryIds);
    }

    @Transactional
    public MenuItem updateMenuItem(Long menuItemId, MenuItemRequest req) {
        MenuItem existing = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Plate not found"));
        MenuItem updated = new MenuItem();
        updated.setId(existing.getId());
        updated.setCategoryId(existing.getCategoryId());
        updated.setName(req.name() != null ? req.name() : existing.getName());
        updated.setDescription(req.description() != null ? req.description() : existing.getDescription());
        updated.setPrice(req.price() != null ? req.price() : existing.getPrice());
        updated.setImageUrl(req.imageUrl() != null ? req.imageUrl() : existing.getImageUrl());

        return menuItemRepository.save(updated);
    }

    public void deleteMenuItem(Long menuItemId) {
        menuItemRepository.deleteById(menuItemId);
    }
}
