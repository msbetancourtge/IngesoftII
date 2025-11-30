package com.clickmunch.menu.repository;

import com.clickmunch.menu.dto.Dessert;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DessertRepository extends CrudRepository<Dessert, Integer> {

    @Query("SELECT * FROM desserts WHERE store_id = :storeId")
    List<Dessert> findByStoreId(@Param("storeId") Integer storeId);

    @Query("SELECT store_id FROM desserts WHERE id = :dessertId")
    Integer findStoreByDessert(@Param("dessertId") Integer dessertId);
}