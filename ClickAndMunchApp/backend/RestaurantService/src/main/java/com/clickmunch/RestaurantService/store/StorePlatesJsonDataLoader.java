package com.clickmunch.RestaurantService.store;

import com.clickmunch.RestaurantService.menu.DessertRepository;
import com.clickmunch.RestaurantService.menu.DrinkRepository;
import com.clickmunch.RestaurantService.menu.PlateRepository;
import com.clickmunch.RestaurantService.store.dto.StoreRequests;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

@Component
@Order(2)
public class StorePlatesJsonDataLoader implements CommandLineRunner {

    private static final Logger log = Logger.getLogger(StorePlatesJsonDataLoader.class.getName());
    private final StoreService storeService;
    private final PlateRepository plateRepository;
    private final DrinkRepository drinkRepository;
    private final DessertRepository dessertRepository;
    private final ObjectMapper objectMapper;

    public StorePlatesJsonDataLoader(StoreService storeService, PlateRepository plateRepository, DrinkRepository drinkRepository,
                                     DessertRepository dessertRepository, ObjectMapper objectMapper) {
        this.storeService = storeService;
        this.plateRepository = plateRepository;
        this.drinkRepository = drinkRepository;
        this.dessertRepository = dessertRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (plateRepository.count() == 0 && drinkRepository.count() == 0 && dessertRepository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/menus.json")) {
                StoreRequests storeRequests = objectMapper.readValue(inputStream, StoreRequests.class);
                log.info("Loaded plates, drinks and desserts data - 8 records");
                storeService.createMenu(storeRequests);
            }
            catch (IOException e) {
                throw new RuntimeException("Failed to load plates, drinks and desserts data", e);
            }
        }
        else {
            log.info("Nothing loaded");
        }
    }
}
