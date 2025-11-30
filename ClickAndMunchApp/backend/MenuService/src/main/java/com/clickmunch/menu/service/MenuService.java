package com.clickmunch.menu.service;

import com.clickmunch.menu.dto.*;
import com.clickmunch.menu.repository.DessertRepository;
import com.clickmunch.menu.repository.DrinkRepository;
import com.clickmunch.menu.repository.PlateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuService {

    private final PlateRepository plateRepository;
    private final DrinkRepository drinkRepository;
    private final DessertRepository dessertRepository;

    public MenuService(PlateRepository plateRepository,
                       DrinkRepository drinkRepository,
                       DessertRepository dessertRepository) {
        this.plateRepository = plateRepository;
        this.drinkRepository = drinkRepository;
        this.dessertRepository = dessertRepository;
    }

    // Get full menu for a store
    public StoreMenuResponse getMenuByStoreId(Integer storeId) {
        List<Plate> plates = plateRepository.findByStoreId(storeId);
        List<Drink> drinks = drinkRepository.findByStoreId(storeId);
        List<Dessert> desserts = dessertRepository.findByStoreId(storeId);
        return new StoreMenuResponse(storeId, plates, drinks, desserts);
    }

    // Create full menu for a store
    @Transactional
    public void createMenu(MenuCreateRequest request) {
        Integer storeId = request.storeId();

        List<Plate> plates = request.plates().stream()
                .map(req -> new Plate(null, storeId, req.name(), req.description(), req.price(), req.image()))
                .toList();
        plateRepository.saveAll(plates);

        List<Drink> drinks = request.drinks().stream()
                .map(req -> new Drink(null, storeId, req.name(), req.description(), req.price(), req.image()))
                .toList();
        drinkRepository.saveAll(drinks);

        List<Dessert> desserts = request.desserts().stream()
                .map(req -> new Dessert(null, storeId, req.name(), req.description(), req.price(), req.image()))
                .toList();
        dessertRepository.saveAll(desserts);
    }

    // Delete all menu items for a store
    @Transactional
    public void deleteMenuByStoreId(Integer storeId) {
        plateRepository.findByStoreId(storeId).forEach(p -> plateRepository.deleteById(p.id()));
        drinkRepository.findByStoreId(storeId).forEach(d -> drinkRepository.deleteById(d.id()));
        dessertRepository.findByStoreId(storeId).forEach(d -> dessertRepository.deleteById(d.id()));
    }

    // Plates
    public Plate createPlate(Integer storeId, PlateRequest req) {
        Plate p = new Plate(null, storeId, req.name(), req.description(), req.price(), req.image());
        return plateRepository.save(p);
    }

    public Plate findPlateById(Integer id) {
        return plateRepository.findById(id).orElseThrow(() -> new RuntimeException("Plate not found"));
    }

    public List<Plate> findPlatesByStoreId(Integer storeId) {
        return plateRepository.findByStoreId(storeId);
    }

    @Transactional
    public Plate updatePlate(Integer plateId, PlateRequest req) {
        Plate existing = plateRepository.findById(plateId)
                .orElseThrow(() -> new RuntimeException("Plate not found"));
        Plate updated = new Plate(
                existing.id(),
                existing.storeId(),
                req.name() != null ? req.name() : existing.name(),
                req.description() != null ? req.description() : existing.description(),
                req.price() != null ? req.price() : existing.price(),
                req.image() != null ? req.image() : existing.image()
        );
        return plateRepository.save(updated);
    }

    public void deletePlate(Integer plateId) {
        plateRepository.deleteById(plateId);
    }

    // Drinks
    public Drink createDrink(Integer storeId, DrinkRequest req) {
        Drink d = new Drink(null, storeId, req.name(), req.description(), req.price(), req.image());
        return drinkRepository.save(d);
    }

    public Drink findDrinkById(Integer id) {
        return drinkRepository.findById(id).orElseThrow(() -> new RuntimeException("Drink not found"));
    }

    public List<Drink> findDrinksByStoreId(Integer storeId) {
        return drinkRepository.findByStoreId(storeId);
    }

    @Transactional
    public Drink updateDrink(Integer drinkId, DrinkRequest req) {
        Drink existing = drinkRepository.findById(drinkId)
                .orElseThrow(() -> new RuntimeException("Drink not found"));
        Drink updated = new Drink(
                existing.id(),
                existing.storeId(),
                req.name() != null ? req.name() : existing.name(),
                req.description() != null ? req.description() : existing.description(),
                req.price() != null ? req.price() : existing.price(),
                req.image() != null ? req.image() : existing.image()
        );
        return drinkRepository.save(updated);
    }

    public void deleteDrink(Integer drinkId) {
        drinkRepository.deleteById(drinkId);
    }

    // Desserts
    public Dessert createDessert(Integer storeId, DessertRequest req) {
        Dessert s = new Dessert(null, storeId, req.name(), req.description(), req.price(), req.image());
        return dessertRepository.save(s);
    }

    public Dessert findDessertById(Integer id) {
        return dessertRepository.findById(id).orElseThrow(() -> new RuntimeException("Dessert not found"));
    }

    public List<Dessert> findDessertsByStoreId(Integer storeId) {
        return dessertRepository.findByStoreId(storeId);
    }

    @Transactional
    public Dessert updateDessert(Integer dessertId, DessertRequest req) {
        Dessert existing = dessertRepository.findById(dessertId)
                .orElseThrow(() -> new RuntimeException("Dessert not found"));
        Dessert updated = new Dessert(
                existing.id(),
                existing.storeId(),
                req.name() != null ? req.name() : existing.name(),
                req.description() != null ? req.description() : existing.description(),
                req.price() != null ? req.price() : existing.price(),
                req.image() != null ? req.image() : existing.image()
        );
        return dessertRepository.save(updated);
    }

    public void deleteDessert(Integer dessertId) {
        dessertRepository.deleteById(dessertId);
    }
}