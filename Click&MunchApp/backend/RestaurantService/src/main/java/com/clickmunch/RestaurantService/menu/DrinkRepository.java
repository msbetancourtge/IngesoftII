package com.clickmunch.RestaurantService.menu;

import com.clickmunch.RestaurantService.menu.dto.Drink;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepository extends CrudRepository<Drink, Integer> {


    @Query("SELECT store_id FROM drinks WHERE id = :drinkId")
    Integer findStoreByDrink(@Param("drinkId") Integer drinkId);


}
