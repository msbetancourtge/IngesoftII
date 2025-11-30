package com.clickmunch.menu.controller;

import com.clickmunch.menu.dto.*;
import com.clickmunch.menu.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    // Get full menu for a store
    @GetMapping("/store/{storeId}")
    public StoreMenuResponse getMenuByStoreId(@PathVariable Integer storeId) {
        return menuService.getMenuByStoreId(storeId);
    }

    // Create full menu for a store
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMenu(@RequestBody MenuCreateRequest request) {
        menuService.createMenu(request);
    }

    // Delete all menu items for a store
    @DeleteMapping("/store/{storeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuByStoreId(@PathVariable Integer storeId) {
        menuService.deleteMenuByStoreId(storeId);
    }

    // Plates
    @GetMapping("/store/{storeId}/plates")
    public List<Plate> getPlatesByStoreId(@PathVariable Integer storeId) {
        return menuService.findPlatesByStoreId(storeId);
    }

    @PostMapping("/store/{storeId}/plates")
    @ResponseStatus(HttpStatus.CREATED)
    public Plate addPlate(@PathVariable Integer storeId, @RequestBody PlateRequest req) {
        return menuService.createPlate(storeId, req);
    }

    @GetMapping("/plates/{plateId}")
    public Plate getPlate(@PathVariable Integer plateId) {
        try {
            return menuService.findPlateById(plateId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plate not found");
        }
    }

    @PutMapping("/plates/{plateId}")
    public Plate editPlate(@PathVariable Integer plateId, @RequestBody PlateRequest req) {
        try {
            return menuService.updatePlate(plateId, req);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/plates/{plateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePlate(@PathVariable Integer plateId) {
        menuService.deletePlate(plateId);
    }

    // Drinks
    @GetMapping("/store/{storeId}/drinks")
    public List<Drink> getDrinksByStoreId(@PathVariable Integer storeId) {
        return menuService.findDrinksByStoreId(storeId);
    }

    @PostMapping("/store/{storeId}/drinks")
    @ResponseStatus(HttpStatus.CREATED)
    public Drink addDrink(@PathVariable Integer storeId, @RequestBody DrinkRequest req) {
        return menuService.createDrink(storeId, req);
    }

    @GetMapping("/drinks/{drinkId}")
    public Drink getDrink(@PathVariable Integer drinkId) {
        try {
            return menuService.findDrinkById(drinkId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Drink not found");
        }
    }

    @PutMapping("/drinks/{drinkId}")
    public Drink editDrink(@PathVariable Integer drinkId, @RequestBody DrinkRequest req) {
        try {
            return menuService.updateDrink(drinkId, req);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/drinks/{drinkId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeDrink(@PathVariable Integer drinkId) {
        menuService.deleteDrink(drinkId);
    }

    // Desserts
    @GetMapping("/store/{storeId}/desserts")
    public List<Dessert> getDessertsByStoreId(@PathVariable Integer storeId) {
        return menuService.findDessertsByStoreId(storeId);
    }

    @PostMapping("/store/{storeId}/desserts")
    @ResponseStatus(HttpStatus.CREATED)
    public Dessert addDessert(@PathVariable Integer storeId, @RequestBody DessertRequest req) {
        return menuService.createDessert(storeId, req);
    }

    @GetMapping("/desserts/{dessertId}")
    public Dessert getDessert(@PathVariable Integer dessertId) {
        try {
            return menuService.findDessertById(dessertId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dessert not found");
        }
    }

    @PutMapping("/desserts/{dessertId}")
    public Dessert editDessert(@PathVariable Integer dessertId, @RequestBody DessertRequest req) {
        try {
            return menuService.updateDessert(dessertId, req);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/desserts/{dessertId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeDessert(@PathVariable Integer dessertId) {
        menuService.deleteDessert(dessertId);
    }
}