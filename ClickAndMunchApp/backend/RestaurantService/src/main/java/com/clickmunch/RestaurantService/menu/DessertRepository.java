package com.clickmunch.RestaurantService.menu;

import com.clickmunch.RestaurantService.menu.dto.Dessert;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DessertRepository extends CrudRepository<Dessert, Integer> {

    @Query("SELECT store_id FROM desserts WHERE id = :dessertId")
    Integer findStoreByDessert(@Param("dessertId") Integer dessertId);

}
