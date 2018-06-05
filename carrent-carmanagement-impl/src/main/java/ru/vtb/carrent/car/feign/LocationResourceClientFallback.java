/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.feign;

import org.springframework.web.bind.annotation.PathVariable;
import ru.vtb.carrent.organization.dto.LocationDto;

import java.util.Collections;
import java.util.List;

/**
 * LocationResourceClientFallback.
 *
 * @author Roman_Meerson
 */
public class LocationResourceClientFallback implements LocationResourceClient {
    @Override
    public List<LocationDto> getLocations() {
        return Collections.emptyList();
    }

    @Override
    public LocationDto getLocation(@PathVariable Long id) {
        final LocationDto locationDto = new LocationDto();
        locationDto.setId(id);
        locationDto.setAddress("undefined");
        locationDto.setLocationTypeInt("0.0");
        locationDto.setLocationTypeLoc("0.0");
        return locationDto;
    }
}
