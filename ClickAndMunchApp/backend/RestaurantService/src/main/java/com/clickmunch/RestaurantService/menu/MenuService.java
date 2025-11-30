package com.clickmunch.RestaurantService.menu;

import com.clickmunch.RestaurantService.menu.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // Plates
    public Plate createPlate(Integer storeId, PlateRequest req) {
        Plate p = new Plate(null, storeId, null, req.name(), req.description(), req.price(), req.image());
        return plateRepository.save(p);
    }

    public Plate findPlateById(Integer id) {
        return plateRepository.findById(id)
            .map(plate -> plate)
            .orElseThrow()        ;
    }

    @Transactional
    public Plate updatePlate(Integer plateId, PlateRequest req) {
        Plate existing = plateRepository.findById(plateId).orElseThrow(() -> new RuntimeException("Plate not found"));
        Plate updated = new Plate(existing.id(), existing.storeId(), existing.key(),
                req.name() != null ? req.name() : existing.name(),
                req.description() != null ? req.description() : existing.description(),
                req.price() != null ? req.price() : existing.price(),
                req.image() != null ? req.image() : existing.image());
        return plateRepository.save(updated);
    }

    public void deletePlate(Integer plateId) {
        plateRepository.deleteById(plateId);
    }

    // Drinks
    public Drink createDrink(Integer storeId, DrinkRequest req) {
        Drink d = new Drink(null, storeId, null, req.name(), req.description(), req.price(), req.image());
        return drinkRepository.save(d);
    }

    @Transactional
    public Drink updateDrink(Integer drinkId, DrinkRequest req) {
        Drink existing = drinkRepository.findById(drinkId).orElseThrow(() -> new RuntimeException("Drink not found"));
        Drink updated = new Drink(existing.id(), existing.storeId(), existing.key(),
                req.name() != null ? req.name() : existing.name(),
                req.description() != null ? req.description() : existing.description(),
                req.price() != null ? req.price() : existing.price(),
                req.image() != null ? req.image() : existing.image());
        return drinkRepository.save(updated);
    }

    public void deleteDrink(Integer drinkId) {
        drinkRepository.deleteById(drinkId);
    }

    // Desserts
    public Dessert createDessert(Integer storeId, DessertRequest req) {
        Dessert s = new Dessert(null, storeId, null, req.name(), req.description(), req.price(), req.image());
        return dessertRepository.save(s);
    }

    @Transactional
    public Dessert updateDessert(Integer dessertId, DessertRequest req) {
        Dessert existing = dessertRepository.findById(dessertId).orElseThrow(() -> new RuntimeException("Dessert not found"));
        Dessert updated = new Dessert(existing.id(), existing.storeId(), existing.key(),
                req.name() != null ? req.name() : existing.name(),
                req.description() != null ? req.description() : existing.description(),
                req.price() != null ? req.price() : existing.price(),
                req.image() != null ? req.image() : existing.image());
        return dessertRepository.save(updated);
    }

    public void deleteDessert(Integer dessertId) {
        dessertRepository.deleteById(dessertId);
    }
}