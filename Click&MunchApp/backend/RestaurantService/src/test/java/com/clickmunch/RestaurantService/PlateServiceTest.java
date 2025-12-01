package com.clickmunch.RestaurantService;

import com.clickmunch.RestaurantService.menu.PlateRepository;
import com.clickmunch.RestaurantService.menu.PlateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlateServiceTest {

    @Mock
    private PlateRepository plateRepository;

    @InjectMocks
    private PlateService plateService;

    @Test
    void findStoreByPlate_returnsStoreIdFromRepository() {
        when(plateRepository.findStoreByPlate(5)).thenReturn(99);
        Integer storeId = plateService.findStoreByPlate(5);
        assertEquals(Integer.valueOf(99), storeId);
    }
}