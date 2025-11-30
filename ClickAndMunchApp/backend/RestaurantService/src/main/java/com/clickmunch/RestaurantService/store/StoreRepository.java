package com.clickmunch.RestaurantService.store;

import com.clickmunch.RestaurantService.store.dto.Store;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StoreRepository extends CrudRepository<Store, Integer> {

    Collection<Store> findByName(String name);
    Store findByEmail(String email);
    default void createAll(List<Store> stores){
        this.saveAll(stores);
    }

    @Modifying
    @Query("UPDATE stores SET name = :name, alias = :alias, password = :password, address = :address, latitude = :latitude, longitude = :longitude WHERE email = :email")
    void updateData(@Param("name") String name,
                    @Param("alias") String alias,
                    @Param("password") String password,
                    @Param("address") String address,
                    @Param("latitude") Double latitude,
                    @Param("longitude") Double longitude,
                    @Param("email") String email
                    );





}
