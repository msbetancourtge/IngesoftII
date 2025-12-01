package com.clickmunch.RestaurantService.menu;

import org.springframework.stereotype.Service;

@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;

    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    public Integer findStoreByDrink(Integer drinkId){
        return drinkRepository.findStoreByDrink(drinkId);
    }
}
