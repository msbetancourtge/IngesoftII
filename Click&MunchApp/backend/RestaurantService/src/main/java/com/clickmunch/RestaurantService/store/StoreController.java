package com.clickmunch.RestaurantService.store;

import com.clickmunch.RestaurantService.store.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("")
    public List<Store> findAll() {
        if (storeService.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No stores found");
        }
        return storeService.findAll();
    }

    @GetMapping("/{id}")
    public Store findById(@PathVariable Integer id) {
        try {
            return storeService.findById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found");
        }
    }

    /* Finds a store by ID as a DTO without password */
    @GetMapping("/{id}/info")
    public StoreResponse viewById(@PathVariable Integer id) {
            try {
            Store store = storeService.findById(id);
            return new StoreResponse(
                    store.id(),
                    store.name(),
                    store.alias(),
                    store.email(),
                    store.address(),
                    store.latitude(),
                    store.longitude(),
                    store.plates(),
                    store.drinks(),
                    store.desserts()
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found");
        }    
    }

    /* Menu-only retrieval endpoint */
    @GetMapping({"/{id}/info/menu", "/{id}/menu"})
    public StoreMenuResponse getMenu(@PathVariable Integer id) {
        try {
            Store store = storeService.findById(id);
            return new StoreMenuResponse(
                store.id(),
                store.name(),
                store.alias(),
                store.plates(),
                store.drinks(),
                store.desserts());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found");
        }
    }

    @GetMapping("/name/{name}")
    public List<Store> findByName(@PathVariable String name) {
        return storeService.findByName(name);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Store store) {
        List<Store> tempStore = storeService.findByName(store.name());
        if (!tempStore.isEmpty()) {
            tempStore.forEach(storeSaved -> {
                if(storeSaved.alias().equals(store.alias())){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Store already exists");
                }
            });
        }
        storeService.create(store);
    }

    @PostMapping("/add-menu")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMenu(@RequestBody StoreRequests storeRequests) {
        storeService.createMenu(storeRequests);
    }

    @PutMapping("/{email}")
    public void update(@PathVariable String email, @RequestBody Store store) {
        if(storeService.findByEmail(email)!=null && Objects.equals(store.email(), email)) {
            storeService.update(store);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No match");
        }
    }

    /* Edits a store given an email. Edits the specified fields in a DTO. */
    @PutMapping("/{email}/edit")
        @ResponseStatus(HttpStatus.OK)
    public void updateStore(@PathVariable String email, @RequestBody StoreUpdateRequest req) {
        try {
            storeService.updateByEmail(email, req);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found");
        }
    }

    @DeleteMapping("/{email}")
    public void delete(@PathVariable String email) {
        try {
            storeService.delete(email);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store does not exist");
        }
    }

}
