package com.clickmunch.RestaurantService.store;

import com.clickmunch.RestaurantService.store.dto.Stores;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

@Component
@Order(1)
public class StoreJsonDataLoader implements CommandLineRunner {

    private static final Logger log = Logger.getLogger(StoreJsonDataLoader.class.getName());
    private final StoreRepository storeRepository;
    private final ObjectMapper objectMapper;

    public StoreJsonDataLoader(StoreRepository storeRepository, ObjectMapper objectMapper) {
        this.storeRepository = storeRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception{
        if(storeRepository.count() == 0){
            try(InputStream inputStream = TypeReference.class.getResourceAsStream("/data/stores.json")) {
                Stores stores = objectMapper.readValue(inputStream, Stores.class);
                storeRepository.createAll(stores.stores());
                log.info("Loaded stores data - 8 records");
            }catch (IOException e) {
                throw new RuntimeException("Failed to load stores data", e);
            }
        }
        else{
            log.info("Nothing loaded");
        }
    }
}
