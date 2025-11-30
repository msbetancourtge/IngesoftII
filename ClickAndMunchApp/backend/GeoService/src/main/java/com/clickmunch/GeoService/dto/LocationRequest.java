package com.clickmunch.GeoService.dto;

import com.clickmunch.GeoService.entity.LocationType;

public record LocationRequest(
    String name,
    LocationType type,
    Double latitude,
    Double longitude
) {
}
