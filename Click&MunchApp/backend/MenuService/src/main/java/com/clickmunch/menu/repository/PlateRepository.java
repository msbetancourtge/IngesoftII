package com.clickmunch.menu.repository;

import com.clickmunch.menu.dto.Plate;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlateRepository extends CrudRepository<Plate, Integer> {

    @Query("SELECT * FROM plates WHERE store_id = :storeId")
    List<Plate> findByStoreId(@Param("storeId") Integer storeId);

    @Query("SELECT store_id FROM plates WHERE id = :plateId")
    Integer findStoreByPlate(@Param("plateId") Integer plateId);
}