package com.clickmunch.menu.repository;

import com.clickmunch.menu.dto.Drink;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkRepository extends CrudRepository<Drink, Integer> {

    @Query("SELECT * FROM drinks WHERE store_id = :storeId")
    List<Drink> findByStoreId(@Param("storeId") Integer storeId);

    @Query("SELECT store_id FROM drinks WHERE id = :drinkId")
    Integer findStoreByDrink(@Param("drinkId") Integer drinkId);
}