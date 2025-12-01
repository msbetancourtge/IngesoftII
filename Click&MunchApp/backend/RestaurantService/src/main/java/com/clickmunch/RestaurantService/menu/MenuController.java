package com.clickmunch.RestaurantService.menu;

import com.clickmunch.RestaurantService.menu.dto.*;
import com.clickmunch.RestaurantService.store.StoreService;
import com.clickmunch.RestaurantService.store.dto.Store;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/stores/{storeId}/menu")
public class MenuController {

    private final MenuService menuService;
    private final StoreService storeService; // optional check to ensure store exists

    public MenuController(MenuService menuService, StoreService storeService) {
        this.menuService = menuService;
        this.storeService = storeService;
    }

    // Plates
    @PostMapping("/plates")
    @ResponseStatus(HttpStatus.CREATED)
    public Plate addPlate(@PathVariable Integer storeId, @RequestBody PlateRequest req) {
        ensureStoreExists(storeId);
        return menuService.createPlate(storeId, req);
    }

    @GetMapping("/plates/{plateId}")
    public Plate getPlate(@PathVariable Integer storeId, @PathVariable Integer plateId) {
        ensureStoreExists(storeId);
        try {
            return menuService.findPlateById(plateId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plate not found");
        }
    }

    @PutMapping("/plates/{plateId}")
    public Plate editPlate(@PathVariable Integer storeId, @PathVariable Integer plateId, @RequestBody PlateRequest req) {
        ensureStoreExists(storeId);
        try {
            return menuService.updatePlate(plateId, req);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/plates/{plateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePlate(@PathVariable Integer storeId, @PathVariable Integer plateId) {
        ensureStoreExists(storeId);
        menuService.deletePlate(plateId);
    }

    // Drinks
    @PostMapping("/drinks")
    @ResponseStatus(HttpStatus.CREATED)
    public Drink addDrink(@PathVariable Integer storeId, @RequestBody DrinkRequest req) {
        ensureStoreExists(storeId);
        return menuService.createDrink(storeId, req);
    }

    @PutMapping("/drinks/{drinkId}")
    public Drink editDrink(@PathVariable Integer storeId, @PathVariable Integer drinkId, @RequestBody DrinkRequest req) {
        ensureStoreExists(storeId);
        try {
            return menuService.updateDrink(drinkId, req);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/drinks/{drinkId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeDrink(@PathVariable Integer storeId, @PathVariable Integer drinkId) {
        ensureStoreExists(storeId);
        menuService.deleteDrink(drinkId);
    }

    // Desserts
    @PostMapping("/desserts")
    @ResponseStatus(HttpStatus.CREATED)
    public Dessert addDessert(@PathVariable Integer storeId, @RequestBody DessertRequest req) {
        ensureStoreExists(storeId);
        return menuService.createDessert(storeId, req);
    }

    @PutMapping("/desserts/{dessertId}")
    public Dessert editDessert(@PathVariable Integer storeId, @PathVariable Integer dessertId, @RequestBody DessertRequest req) {
        ensureStoreExists(storeId);
        try {
            return menuService.updateDessert(dessertId, req);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/desserts/{dessertId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeDessert(@PathVariable Integer storeId, @PathVariable Integer dessertId) {
        ensureStoreExists(storeId);
        menuService.deleteDessert(dessertId);
    }

    private void ensureStoreExists(Integer storeId) {
        try {
            Store s = storeService.findById(storeId);
            if (s == null) throw new RuntimeException("Store not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found");
        }
    }
}