package com.clickmunch.RestaurantService.menu;

import org.springframework.stereotype.Service;

@Service
public class PlateService {

    private final PlateRepository plateRepository;


    public PlateService(PlateRepository plateRepository) {
        this.plateRepository = plateRepository;
    }

    public Integer findStoreByPlate(Integer plateId){
        return plateRepository.findStoreByPlate(plateId);
    }

}
