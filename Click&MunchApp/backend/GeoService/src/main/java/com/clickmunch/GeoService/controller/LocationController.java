package com.clickmunch.GeoService.controller;

import com.clickmunch.GeoService.dto.LocationRequest;
import com.clickmunch.GeoService.dto.NearbySearchRequest;
import com.clickmunch.GeoService.entity.Location;
import com.clickmunch.GeoService.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geo")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/locations")
    public ResponseEntity<Location> addLocation(@RequestBody LocationRequest locationRequest) {
        Location location = new Location();
        location.setName(locationRequest.name());
        location.setType(locationRequest.type());
        location.setLatitude(locationRequest.latitude());
        location.setLongitude(locationRequest.longitude());
        return ResponseEntity.ok(locationService.save(location));
    }

    @PostMapping("/nearby")
    public ResponseEntity<List<Location>> getNearbyLocations(@RequestBody NearbySearchRequest nearbySearchRequest){
        List<Location> locations = locationService.findNearby(
                nearbySearchRequest.latitude(),
                nearbySearchRequest.longitude(),
                nearbySearchRequest.radiusInKm()
        );
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.findAll();
        return ResponseEntity.ok(locations);
    }


    }
