package com.clickmunch.RestaurantService;

import com.clickmunch.RestaurantService.menu.MenuService;
import com.clickmunch.RestaurantService.menu.PlateRepository;
import com.clickmunch.RestaurantService.menu.DrinkRepository;
import com.clickmunch.RestaurantService.menu.DessertRepository;
import com.clickmunch.RestaurantService.menu.dto.Plate;
import com.clickmunch.RestaurantService.menu.dto.PlateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private PlateRepository plateRepository;

    @Mock
    private DrinkRepository drinkRepository;

    @Mock
    private DessertRepository dessertRepository;

    @InjectMocks
    private MenuService menuService;

    @Test
    void createPlate_savesAndReturnsPlate() {
        PlateRequest req = new PlateRequest("Pasta", "Delicious", 10.5, "img.png");
        when(plateRepository.save(any(Plate.class))).thenAnswer(inv -> inv.getArgument(0));

        Plate created = menuService.createPlate(42, req);

        assertEquals(Integer.valueOf(42), created.storeId());
        assertEquals("Pasta", created.name());
        assertEquals("Delicious", created.description());
        assertEquals(Double.valueOf(10.5), created.price());
    }

    @Test
    void findPlateById_whenExists_returnsPlate() {
        Plate p = new Plate(1, 42, null, "Taco", "Spicy", 5.0, "taco.png");
        when(plateRepository.findById(1)).thenReturn(Optional.of(p));

        Plate found = menuService.findPlateById(1);

        assertSame(p, found);
    }

    @Test
    void updatePlate_appliesPartialUpdatesAndSaves() {
        Plate existing = new Plate(1, 42, null, "Old", "OldDesc", 5.0, "old.png");
        when(plateRepository.findById(1)).thenReturn(Optional.of(existing));
        when(plateRepository.save(any(Plate.class))).thenAnswer(inv -> inv.getArgument(0));

        PlateRequest updateReq = new PlateRequest("New", null, 7.5, null);
        Plate updated = menuService.updatePlate(1, updateReq);

        assertEquals("New", updated.name());
        assertEquals("OldDesc", updated.description()); // unchanged
        assertEquals(Double.valueOf(7.5), updated.price());
    }

    @Test
    void deletePlate_invokesRepositoryDelete() {
        doNothing().when(plateRepository).deleteById(1);
        menuService.deletePlate(1);
        verify(plateRepository).deleteById(1);
    }
}