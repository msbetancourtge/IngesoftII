package com.clickmunch.RestaurantService.menu;

import org.springframework.stereotype.Service;

@Service
public class DessertService {

    private final DessertRepository dessertRepository;

    public DessertService(DessertRepository dessertRepository) {
        this.dessertRepository = dessertRepository;
    }

    public Integer findStoreByDessert(Integer dessertId){
        return dessertRepository.findStoreByDessert(dessertId);
    }
}
