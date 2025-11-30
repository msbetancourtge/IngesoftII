package com.clickmunch.RestaurantService.store;

import com.clickmunch.RestaurantService.menu.*;
import com.clickmunch.RestaurantService.menu.dto.Dessert;
import com.clickmunch.RestaurantService.menu.dto.Drink;
import com.clickmunch.RestaurantService.menu.dto.Plate;
import com.clickmunch.RestaurantService.store.dto.Store;
import com.clickmunch.RestaurantService.store.dto.StoreRequest;
import com.clickmunch.RestaurantService.store.dto.StoreRequests;
import com.clickmunch.RestaurantService.store.dto.StoreUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final PlateRepository plateRepository;
    private final DrinkRepository drinkRepository;
    private final DessertRepository dessertRepository;

    public StoreService(StoreRepository storeRepository,
                        PlateRepository plateRepository,
                        DrinkRepository drinkRepository,
                        DessertRepository dessertRepository) {
        this.storeRepository = storeRepository;
        this.plateRepository = plateRepository;
        this.drinkRepository = drinkRepository;
        this.dessertRepository = dessertRepository;
    }

    public List<Store> findAll() {
        return (List<Store>) storeRepository.findAll();
    }

    public Store findById(Integer id) {
        return storeRepository.findById(id)
                .map(store -> store)
                .orElseThrow();
    }

    public List<Store> findByName(String name) {
        return (List<Store>) storeRepository.findByName(name);
    }

    public Store findByEmail(String email) {
        return storeRepository.findByEmail(email);
    }

    public void create(Store store) {
        storeRepository.save(store);
    }

    public void createMenu(StoreRequests storeRequests) {
        for (StoreRequest menu : storeRequests.storeRequests()){
            Integer storeId = menu.storeId();
            Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("Store not found"));

            List<Plate> plates = menu.plates().stream()
                    .map(plateRequest -> new Plate(null, store.id(), null, plateRequest.name(), plateRequest.description(), plateRequest.price(), plateRequest.image()))
                            .toList();
            plateRepository.saveAll(plates);

            List<Drink> drinks = menu.drinks().stream()
                    .map(drinkRequest -> new Drink(null, store.id(), null, drinkRequest.name(), drinkRequest.description(), drinkRequest.price(), drinkRequest.image()))
                            .toList();
            drinkRepository.saveAll(drinks);

            List<Dessert> desserts = menu.desserts().stream()
                    .map(dessertRequest -> new Dessert(null, store.id(), null, dessertRequest.name(), dessertRequest.description(), dessertRequest.price(), dessertRequest.image()))
                            .toList();
            dessertRepository.saveAll(desserts);
        }
    }

    @Transactional
    public void update(Store store) {
        storeRepository.updateData(
                store.name(),
                store.alias(),
                store.password(),
                store.address(),
                store.latitude(),
                store.longitude(),
                store.email());
    }

    @Transactional
    public void updateByEmail(String email, StoreUpdateRequest request) {
        Store exists = storeRepository.findByEmail(email);
        if (exists == null)
                        throw new RuntimeException("Store not found");

        // Use provided values or keep existing ones when null
        String name = request.name() != null ? request.name() : exists.name();
        String alias = request.alias() != null ? request.alias() : exists.alias();
        String password = request.password() != null ? request.password() : exists.password();
        String address = request.address() != null ? request.address() : exists.address();
        Double latitude = request.latitude() != null ? request.latitude() : exists.latitude();
        Double longitude = request.longitude() != null ? request.longitude() : exists.longitude();

        storeRepository.updateData(name, alias, password, address, latitude, longitude, email);
    }

    public void delete(String email) {
        storeRepository.deleteById(storeRepository.findByEmail(email).id());
    }

}
